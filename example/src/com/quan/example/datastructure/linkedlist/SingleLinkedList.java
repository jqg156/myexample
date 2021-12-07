package com.quan.example.datastructure.linkedlist;

public class SingleLinkedList {
    private int size;//�����ڵ�ĸ���
    private Node head;//ͷ�ڵ�
    
    public SingleLinkedList(){
        size = 0;
        head = null;
    }
    
    //������ÿ���ڵ���
    private class Node{
        private Object data;//ÿ���ڵ������
        private Node next;//ÿ���ڵ�ָ����һ���ڵ������
        
        public Node(Object data){
            this.data = data;
        }
    }
    
    //������ͷ����Ԫ��
    public Object addHead(Object obj){
        Node newHead = new Node(obj);
        if(size == 0){
            head = newHead;
        }else{
            newHead.next = head;
            head = newHead;
        }
        size++;
        return obj;
    }
    
    //������ͷɾ��Ԫ��
    public Object deleteHead(){
        Object obj = head.data;
        head = head.next;
        size--;
        return obj;
    }
    
    //����ָ��Ԫ�أ��ҵ��˷��ؽڵ�Node���Ҳ�������null
    public Node find(Object obj){
        Node current = head;
        int tempSize = size;
        while(tempSize > 0){
            if(obj.equals(current.data)){
                return current;
            }else{
                current = current.next;
            }
            tempSize--;
        }
        return null;
    }
    
    //ɾ��ָ����Ԫ�أ�ɾ���ɹ�����true
    public boolean delete(Object value){
        if(size == 0){
            return false;
        }
        Node current = head;
        Node previous = head;
        while(current.data != value){
            if(current.next == null){
                return false;
            }else{
                previous = current;
                current = current.next;
            }
        }
        //���ɾ���Ľڵ��ǵ�һ���ڵ�
        if(current == head){
            head = current.next;
            size--;
        }else{//ɾ���Ľڵ㲻�ǵ�һ���ڵ�
            previous.next = current.next;
            size--;
        }
        return true;
    }
    
    //�ж������Ƿ�Ϊ��
    public boolean isEmpty(){
        return (size == 0);
    }
    
    //��ʾ�ڵ���Ϣ
    public void display(){
        if(size >0){
            Node node = head;
            int tempSize = size;
            if(tempSize == 1){//��ǰ����ֻ��һ���ڵ�
                System.out.println("["+node.data+"]");
                return;
            }
            while(tempSize>0){
                if(node.equals(head)){
                    System.out.print("["+node.data+"->");
                }else if(node.next == null){
                    System.out.print(node.data+"]");
                }else{
                    System.out.print(node.data+"->");
                }
                node = node.next;
                tempSize--;
            }
            System.out.println();
        }else{//�������һ���ڵ㶼û�У�ֱ�Ӵ�ӡ[]
            System.out.println("[]");
        }
        
    }

}