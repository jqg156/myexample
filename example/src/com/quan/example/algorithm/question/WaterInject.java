package com.quan.example.algorithm.question;

import java.util.ArrayList;
import java.util.List;

public class WaterInject {
	//��ˮ�ݻ�
	static int volume = 0; 
	//ǽ��
	static int[] wallHeights = new int[]{ 6, 6, 1, 4, 10, 5, 15, 150, 15, 10, 1, 9 };
	
	/**
	 * ������Ч�ڵ㣺����wallHeights���飬�ѡ���Ч���ڵ㣨�����Ƿ���Դ�ˮ�Ľڵ㣩ɸѡ������ɸѡ�������£�
	 * 1.�����ͷ��㣬��ô����Ҫ�����Ҳ�Ľڵ�
	 * 2.������м�ڵ㣬��ô������ڵ�������Ľڵ�
	 * 3.�����ĩ�ڵ㣬��ô����������Ľڵ�
	 * ����Ч�ڵ�֮��Ŀɴ�ˮ��Ȼ���ȥ�Ѿ�������ֵ
	 * @param wallHeights
	 */
	private static void effectiveNode(int[] wallHeights) {
		List<Integer> list = new ArrayList<>();
		//�������飬�ҳ��ܹ������Ƿ���Դ�ˮ�Ľڵ�
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
	 * һ���ڵ�Ĵ�ˮ��ȡ���ڵ�ǰ�ڵ��������ֵ���Ҳ�����ֵ�еĽ�С���뵱ǰֵ�Ĳ�
	 * ���ԣ�ֻҪ��һ����ָ����ֵ�������ȷ����ǰ������ֵ���Ա�������
	 * min(maxLeft,maxRight) - currentValue
	 * ֻ��һ�α���������ָ��һ�����������ƶ���һ�����������ƶ������ֱ��¼ָ��;�о��������ֵ��
	 * �����ߵ�����ֵС���ұߣ������ָ�������ƶ��������ұ�ָ�������ƶ���ֱ��������
	 * @param wallHeights
	 */
	private static void doublePointer(int[] wallHeights) {
		//�������ҵ�ָ��
		int indexLeft = 0;
		//���������ָ��
		int indexRight = wallHeights.length - 1;
		//��������;�о��������ֵ
		int maxLeft = wallHeights[indexLeft];
		//��������;�о��������ֵ
		int maxRight = wallHeights[indexRight];
		//��ָ��û������֮ǰִ�в���
		while(indexLeft < indexRight) {
			//������ֵС���Ҳ�����ָ������
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
	 * һ���ڵ�Ĵ�ˮ��ȡ���ڵ�ǰ�ڵ��������ֵ���Ҳ�����ֵ�еĽ�С���뵱ǰֵ�Ĳ�
	 * min(maxLeft,maxRight) - currentValue
	 * ���α��������ҵ����ֵ��������������ֵ��Ȼ����ұ��������ֵ
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
	 * ���������������Ч�ڵ㣬���͵ڶ�����еݹ鴦��
	 * ȡ�������ǽ֮���ˮ�ݻ����ݹ鴦���㷨���Ӷ�O(n!)
	 * @param start
	 * @param end
	 */
	private static void recursiveTwoEffectiveNodes(int start, int end)
	{
		// first��start��end֮����ߵ�ǽ
		// second��start��end֮��ڶ��ߵ�ǽ
		int first = 0, second = 0;
		// firstIndex����һ�ߵ�ǽ��wallHeights�е�����
		// secondIndex���ڶ��ߵ�ǽ��wallHeights�е�����
		int firstIndex = 0, secondIndex = 0;
		// ����ǽ֮�����������һ��ǽ�ľ���
		if (end - start <= 1)
			return;
		// ��ʼ��ȡ��һ�ߺ͵ڶ���ǽ��ש��
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

		// ��ȡ���ǽ������
		int startIndex = Math.min(firstIndex, secondIndex);
		// ��ȡ�Ҳ�ǽ������
		int endIndex = Math.max(firstIndex, secondIndex);
		// �������
		int distance = endIndex - startIndex;

		// �����һ�ߵ�ǽ�͵ڶ��ߵ�ǽ֮��������һ��ǽ����ô��ʼ����������ǽ֮����ԷŶ��ٸ���λ��ˮ
		if (distance > 1)
		{
			volume = volume + (distance - 1) * second;
			// ��ȥ������ǽ֮���ש��
			for (int i = startIndex + 1; i < endIndex; i++)
			{
				volume -= wallHeights[i];
			}
		}
		// ��ʼ�ݹ鴦�����ǽ���뿪ʼλ���ܷŶ���ˮ
		recursiveTwoEffectiveNodes(start, startIndex);
		// ��ʼ�ݹ鴦���Ҳ�ǽ�������λ���ܷŶ���ˮ
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
