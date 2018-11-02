package com.quan.example.algorithm.question;

import java.util.ArrayList;
import java.util.List;

public class WaterInject {
	//积水容积
	static int volume = 0; 
	//墙高
	static int[] wallHeights = new int[]{ 6, 6, 1, 4, 10, 5, 15, 150, 15, 10, 1, 9 };
	
	/**
	 * 借助有效节点：遍历wallHeights数组，把“有效”节点（决定是否可以存水的节点）筛选出来，筛选规则如下：
	 * 1.如果是头结点，那么必须要大于右侧的节点
	 * 2.如果是中间节点，那么必须大于等于两侧的节点
	 * 3.如果是末节点，那么必须大于左侧的节点
	 * 求有效节点之间的可存水，然后减去已经填充的数值
	 * @param wallHeights
	 */
	private static void effectiveNode(int[] wallHeights) {
		List<Integer> list = new ArrayList<>();
		//遍历数组，找出能够决定是否可以存水的节点
		for(int i = 0;i < wallHeights.length; i++) {
			if(i == wallHeights.length - 1) {
				if(wallHeights[i] > wallHeights[i - 1]) {
					list.add(i);
				}
				break;
			}
			if(wallHeights[i] > wallHeights[i + 1]) {
				if(i == 0) {
					list.add(i);
				}else {
					if(wallHeights[i] >= wallHeights[i - 1]) {
						list.add(i);
					}
				}
			}
		}
		
		for(int i = 0;i < list.size() - 1; i++) {
			int beginIndex = list.get(i);
			int endIndex = list.get(i + 1);
			int begin = wallHeights[beginIndex];
			int end = wallHeights[endIndex];
			int hor = endIndex - beginIndex - 1;
			int ver = Math.min(begin, end);
			int sum = hor * ver;
			int filled = 0;
			for(int j = beginIndex  + 1;j < endIndex;j++) {
				filled = filled + (wallHeights[j] > ver ? ver : wallHeights[j]);
			}
			volume = volume + (sum - filled);
		}
	}
	
	/**
	 * 一个节点的存水，取决于当前节点左侧的最大值与右侧的最大值中的较小者与当前值的差
	 * 所以，只要另一侧出现更大的值，则可以确保当前侧的最大值可以被填满。
	 * min(maxLeft,maxRight) - currentValue
	 * 只需一次遍历，两个指针一个从左往右移动，一个从右向左移动，并分别记录指针途中经历的最大值；
	 * 如果左边的做大值小于右边，则左边指针往右移动；否则，右边指针向左移动；直到相遇。
	 * @param wallHeights
	 */
	private static void doublePointer(int[] wallHeights) {
		//从左往右的指针
		int indexLeft = 0;
		//从右往左的指针
		int indexRight = wallHeights.length - 1;
		//从左向右途中经历的最大值
		int maxLeft = wallHeights[indexLeft];
		//从右向左途中经历的最大值
		int maxRight = wallHeights[indexRight];
		//两指针没有相遇之前执行操作
		while(indexLeft < indexRight) {
			//左侧最大值小于右侧则左指针右移
			if(maxLeft <= maxRight) {
				indexLeft = indexLeft + 1;
				if(maxLeft <= wallHeights[indexLeft]) {
					maxLeft = wallHeights[indexLeft];
				}else {
					volume = volume + (maxLeft - wallHeights[indexLeft]);
				}
			}else {
				indexRight = indexRight - 1;
				if(maxRight <= wallHeights[indexRight]) {
					maxRight = wallHeights[indexRight];
				}else {
					volume = volume + (maxRight - wallHeights[indexRight]);
				}
			}
		}
	}
	
	/**
	 * 一个节点的存水，取决于当前节点左侧的最大值与右侧的最大值中的较小者与当前值的差
	 * min(maxLeft,maxRight) - currentValue
	 * 两次遍历，先找到最大值，从左遍历到最大值，然后从右遍历到最大值
	 * @param wallHeights
	 */
	private static void seekMaxValue(int[] wallHeights) {
		int index = 0;
		int indexLeft = 0;
		int indexRight = wallHeights.length - 1;
		int max = wallHeights[index];
		int maxLeft = wallHeights[indexLeft];
		int maxRight = wallHeights[indexRight];
		
		for(int i = 0; i < wallHeights.length; i++) {
			if(max < wallHeights[i]) {
				max = wallHeights[i];
				index = i;
			}
		}
		
		for(int i = 0;i < index; i ++) {
			indexLeft = indexLeft + 1;
			if(maxLeft <= wallHeights[indexLeft]) {
				maxLeft = wallHeights[indexLeft];
			}else {
				volume = volume + (maxLeft - wallHeights[indexLeft]);
			}
		}
		
		for(int i = wallHeights.length - 1;i > index; i --) {
			indexRight = indexRight - 1;
			if(maxRight <= wallHeights[indexRight]) {
				maxRight = wallHeights[indexRight];
			}else {
				volume = volume + (maxRight - wallHeights[indexRight]);
			}
		}
	}
	
	/**
	 * 借助特殊的两个有效节点，最大和第二大进行递归处理
	 * 取两个最高墙之间积水容积，递归处理，算法复杂度O(n!)
	 * @param start
	 * @param end
	 */
	private static void recursiveTwoEffectiveNodes(int start, int end)
	{
		// first：start和end之间最高的墙
		// second：start和end之间第二高的墙
		int first = 0, second = 0;
		// firstIndex：第一高的墙在wallHeights中的索引
		// secondIndex：第二高的墙在wallHeights中的索引
		int firstIndex = 0, secondIndex = 0;
		// 两堵墙之间必须至少有一堵墙的距离
		if (end - start <= 1)
			return;
		// 开始获取第一高和第二高墙的砖数
		for (int i = start; i <= end; i++)
		{
			if (wallHeights[i] > first)
			{
				second = first;
				secondIndex = firstIndex;
				first = wallHeights[i];
				firstIndex = i;
			}
			else if (wallHeights[i] > second)
			{
				second = wallHeights[i];
				secondIndex = i;
			}
		}

		// 获取左侧墙的索引
		int startIndex = Math.min(firstIndex, secondIndex);
		// 获取右侧墙的索引
		int endIndex = Math.max(firstIndex, secondIndex);
		// 计算距离
		int distance = endIndex - startIndex;

		// 如果第一高的墙和第二高的墙之间至少有一堵墙，那么开始计算这两堵墙之间可以放多少个单位的水
		if (distance > 1)
		{
			volume = volume + (distance - 1) * second;
			// 减去这两堵墙之间的砖数
			for (int i = startIndex + 1; i < endIndex; i++)
			{
				volume -= wallHeights[i];
			}
		}
		// 开始递归处理左侧墙距离开始位置能放多少水
		recursiveTwoEffectiveNodes(start, startIndex);
		// 开始递归处理右侧墙距离结束位置能放多少水
		recursiveTwoEffectiveNodes(endIndex, end);
	}

	public static void main(String[] args)
	{
		//recursiveTwoEffectiveNodes(0, wallHeights.length - 1);
		//doublePointer(wallHeights);
		//seekMaxValue(wallHeights);
		effectiveNode(wallHeights);
		System.out.println(volume);
	}
}
