import java.util.Arrays;

class Solution4 {
    public int maximumGap(int[] nums) {
        int n = nums.length;

        if (n < 2) {
            return 0;
        }

        // Find the maximum value in the array
        int maxVal = Arrays.stream(nums).max().getAsInt();

        // Initialize variables
        long exp = 1;
        int[] buf = new int[n];

        while (maxVal >= exp) {
            int[] cnt = new int[10];

            // Count the occurrences of each digit at the current exponent position
            for (int i = 0; i < n; i++) {
                int digit = (nums[i] / (int) exp) % 10;
                cnt[digit]++;
            }

            // Calculate cumulative counts
            for (int i = 1; i < 10; i++) {
                cnt[i] += cnt[i - 1];
            }

            // Place elements in their correct position based on the current digit
            for (int i = n - 1; i >= 0; i--) {
                int digit = (nums[i] / (int) exp) % 10;
                buf[cnt[digit] - 1] = nums[i];
                cnt[digit]--;
            }

            // Copy the sorted result from buf back to nums
            System.arraycopy(buf, 0, nums, 0, n);

            // Increase the exponent by 10 for the next iteration
            exp *= 10;
        }

        // Calculate the maximum gap
        int maxGap = 0;
        for (int i = 1; i < n; i++) {
            maxGap = Math.max(maxGap, nums[i] - nums[i - 1]);
        }

        return maxGap;
    }
}