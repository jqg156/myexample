package com.quan.example.datastructure.linkedlist;

public class TwoWayLinkedList {
    private Node head;//��ʾ����ͷ
    private Node tail;//��ʾ����β
    private int size;//��ʾ����Ľڵ����
    
    private class Node{
        private Object data;
        private Node next;
        private Node prev;
        
        public Node(Object data){
            this.data = data;
        }
    }
    
    public TwoWayLinkedList(){
        size = 0;
        head = null;
        tail = null;
    }
    
    //������ͷ���ӽڵ�
    public void addHead(Object value){
        Node newNode = new Node(value);
        if(size == 0){
            head = newNode;
            tail = newNode;
            size++;
        }else{
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        }
    }
    
    //������β���ӽڵ�
    public void addTail(Object value){
        Node newNode = new Node(value);
        if(size == 0){
            head = newNode;
            tail = newNode;
            size++;
        }else{
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }
    
    //ɾ������ͷ
    public Node deleteHead(){
        Node temp = head;
        if(size != 0){
            head = head.next;
            head.prev = null;
            size--;
        }
        return temp;
    }
    
    //ɾ������β
    public Node deleteTail(){
        Node temp = tail;
        if(size != 0){
            tail = tail.prev;
            tail.next = null;
            size--;
        }
        return temp;
    }
    
    //�������Ľڵ����
    public int getSize(){
        return size;
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