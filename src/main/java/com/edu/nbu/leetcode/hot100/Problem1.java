package com.edu.nbu.leetcode.hot100;

import com.edu.nbu.PrintUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 */
public class Problem1 {

    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;
        PrintUtils.print(twoSum(nums,target));
        PrintUtils.print(twoSum2(nums,target));
        PrintUtils.print(twoSum3(nums,target));
    }

    //暴力 ,时间复杂度O(n2) ，空间复杂度O(1)
    public static  int[] twoSum(int[] nums,int target){
        for(int i=0;i<nums.length;i ++){
            for(int j= i+1;j<nums.length;j++){
                if(nums[i] + nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    //借用hashmap,两次hash表，时间复杂度O(n),空间复杂度O(n)
    public static int[]  twoSum2(int[] nums,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);
        }

        for(int i=0;i<nums.length;i++){
            int complement = target - nums[i];
            if(map.containsKey(complement) && map.get(complement) != i){
                return new int[]{i,map.get(complement)};
            }
        }
        return null;
    }

    //一次hashmap，时间复杂度O(n),空间复杂度O(n)
    public static int[] twoSum3(int[]nums,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int complement = target - nums[i];
            if(map.containsKey(complement)){
                return new int[]{map.get(complement),i};
            }
            map.put(nums[i],i);
        }
        return null;
    }
}
