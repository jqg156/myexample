package com.quan.example.datastructure.linkedlist;

public class StackSingleLink {
    private SingleLinkedList link;
    
    public StackSingleLink(){
        link = new SingleLinkedList();
    }
    
    //���Ԫ��
    public void push(Object obj){
        link.addHead(obj);
    }
    
    //�Ƴ�ջ��Ԫ��
    public Object pop(){
        Object obj = link.deleteHead();
        return obj;
    }
    
    //�ж��Ƿ�Ϊ��
    public boolean isEmpty(){
        return link.isEmpty();
    }
    
    //��ӡջ��Ԫ����Ϣ
    public void display(){
        link.display();
    }

}