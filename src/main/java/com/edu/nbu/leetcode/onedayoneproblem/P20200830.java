package com.edu.nbu.leetcode.onedayoneproblem;

import com.edu.nbu.PrintUtils;

import java.util.*;

/**
 * 最小区间,类似题目：https://leetcode-cn.com/problems/minimum-window-substring/
 * 题目说明：
 * 你有 k 个升序排列的整数列表。找到一个最小区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
 * <p>
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/smallest-range-covering-elements-from-k-lists
 */
public class P20200830 {

    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        List<Integer> list1 = Arrays.asList(new Integer[]{4, 10, 15, 24, 26});
        List<Integer> list2 = Arrays.asList(new Integer[]{0, 9, 12, 20});
        List<Integer> list3 = Arrays.asList(new Integer[]{5, 18, 22, 30});
        nums.add(list1);
        nums.add(list2);
        nums.add(list3);
        PrintUtils.print(smallestRange(nums));
        PrintUtils.print(smallestRange2(nums));
    }

    /**
     * 方法1,堆
     * 时间复杂度：O(nk \log k)O(nklogk),空间复杂度：O(k)O(k)
     */
    public static int[] smallestRange(List<List<Integer>> nums) {
        //区间的左边和右边
        int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
        //最小范围
        int minRange = rangeRight - rangeLeft;
        //区间的左边最大值
        int max = Integer.MIN_VALUE;
        int size = nums.size();
        //由于 k 个列表都是升序排列的，因此对每个列表维护一个指针，
        //通过指针得到列表中的元素，指针右移之后指向的元素一定大于或等于之前的元素。
        int[] next = new int[size];
        //使用最小堆维护 k 个指针指向的元素中的最小值
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            public int compare(Integer index1, Integer index2) {
                //第index个列表的next[index1]指针指向的元素
                return nums.get(index1).get(next[index1]) - nums.get(index2).get(next[index2]);
            }
        });

        for (int i = 0; i < size; i++) {
            //初始时，i 个指针都指向下标 0，因为next[i]=0
            priorityQueue.offer(i);
            //最大元素即为所有列表的下标 0 位置的元素中的最大值
            max = Math.max(max, nums.get(i).get(0));
        }

        while (true) {
            //每次从堆中取出最小值，minIndex是指第几个列表，也代表指针数组next的下标
            int minIndex = priorityQueue.poll();
            //根据最大值和最小值计算当前区间
            int curRange = max - nums.get(minIndex).get(next[minIndex]);
            //如果当前区间小于最小区间则用当前区间更新最小区间
            if (curRange < minRange) {
                minRange = curRange;
                rangeLeft = nums.get(minIndex).get(next[minIndex]);
                rangeRight = max;
            }
            //然后将对应列表的指针右移
            next[minIndex]++;
            //如果一个列表的指针超出该列表的下标范围，则说明该列表中的所有元素都被遍历过，
            //堆中不会再有该列表中的元素，因此退出循环。
            if (next[minIndex] == nums.get(minIndex).size()) {
                break;
            }
            //将新元素加入堆中
            priorityQueue.offer(minIndex);
            //并更新堆中元素的最大值
            max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
        }
        return new int[]{rangeLeft, rangeRight};
    }

    /**
     * 方法2 hash表 + 滑动窗口
     * 时间复杂度：O(nk + |V|)O(nk+∣V∣)，其中 nn 是所有列表的平均长度，kk 是列表数量，|V|∣V∣ 是列表中元素的值域，在本题中 |V| <= 2*10^5
     * 空间复杂度：O(nk)O(nk)
     *
     * @param nums
     * @return
     */
    public static int[] smallestRange2(List<List<Integer>> nums) {
        int size = nums.size();
        Map<Integer, List<Integer>> indices = new HashMap<Integer, List<Integer>>();
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            for (int x : nums.get(i)) {
                List<Integer> list = indices.getOrDefault(x, new ArrayList<Integer>());
                list.add(i);
                indices.put(x, list);
                xMin = Math.min(xMin, x);
                xMax = Math.max(xMax, x);
            }
        }

        int[] freq = new int[size];
        int inside = 0;
        int left = xMin, right = xMin - 1;
        int bestLeft = xMin, bestRight = xMax;

        while (right < xMax) {
            right++;
            if (indices.containsKey(right)) {
                for (int x : indices.get(right)) {
                    freq[x]++;
                    if (freq[x] == 1) {
                        inside++;
                    }
                }
                while (inside == size) {
                    if (right - left < bestRight - bestLeft) {
                        bestLeft = left;
                        bestRight = right;
                    }
                    if (indices.containsKey(left)) {
                        for (int x : indices.get(left)) {
                            freq[x]--;
                            if (freq[x] == 0) {
                                inside--;
                            }
                        }
                    }
                    left++;
                }
            }
        }

        return new int[]{bestLeft, bestRight};
    }

}
