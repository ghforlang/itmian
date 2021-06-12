package com.edu.nbu.leetcode;

import java.util.List;

/**
 * 两数相加
 */
public class Problem2 {

    private static final ListNode l1 = new ListNode(2,new ListNode(4,new ListNode(3)));
    private static final ListNode l2 = new ListNode(5,new ListNode(6,new ListNode(4)));
    private static final ListNode l3 = new ListNode(0);
    private static final ListNode l4 = new ListNode(7,new ListNode(3));
    private static final ListNode l5 = new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9)))))));
    private static final ListNode l6 = new ListNode(9,new ListNode(9,new ListNode(9,new ListNode(9))));

     private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 官方答案
    public static  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int carry = 0;

        while(l1 != null || l2 != null){
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;

            int sum = x + y + carry;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            carry = sum / 10;

            if(l1 != null){
                l1 = l1.next;
            }
            if(l2 != null){
                l2 = l2.next;
            }
        }
        if(carry > 0){
            cur.next = new ListNode(carry);
        }
        return dummy.next;
    }

    // 自己的答案,结果长度以l1为主，先计算相等长度的数据，再追加剩余数据即可
    public static  ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode result = l1;
        ListNode cur = l1;
        int len1 = length(l1);
        int len2 = length(l2);
        int maxLen = len1 > len2 ? len1 : len2;
        while(maxLen -- > 0){
            if(l1 != null && l2 != null){
                l1.val += l2.val;
                int temp = l1.val;
                if(temp >= 10){
                    l1.val = temp % 10;
                    if(l1.next != null){
                        l1.next.val += (temp / 10);
                    }else{
                        l1.next = new ListNode(temp / 10);
                    }
                }
                cur = l1;
                l1 = l1.next;
                l2 = l2.next;
            }
        }
        cur.next = l1 != null ? l1 : l2;
        while (cur != null){
            int temp = cur.val;
            if(temp >= 10){
                l1.val = temp % 10;
                if(cur.next != null){
                    cur.next.val += (temp / 10);
                }else{
                    cur.next = new ListNode(temp /10);
                }
            }
            cur = cur.next;
        }
        return result;
    }

    //辅助方法
    public static int length(ListNode l){
         int length = 0;
         while(l != null){
             length ++;
             l = l.next;
         }
         return length;
    }

    public static void main(String[] args) {
        print(addTwoNumbers2(l1,l2));
        print(addTwoNumbers2(l3,l4));
        print(addTwoNumbers2(l5,l6));
    }

    private static void print(ListNode l){
        System.out.print("[");
         while(l != null){
             System.out.print(l.val + ",");
             l = l.next;
         }
        System.out.println("]");
    }

}
