package com.sachin.risk.common.data.idnumber;

/**
 * @author shicheng.zhang
 * @date 17-4-1
 * @time 上午11:17
 * @Description:
 */
public class IdNumberUtil {

    private static int[] weight = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; // 十七位数字本体码权重
    private static char[] validate = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // mod11,对应校验码字符值

    /**
     * 根据身份证号前17位计算第18位校验位
     * @param id17
     * @return
     */
    public static char getValidateCode(String id17) {
        int sum = 0;
        int mode = 0;
        for (int i = 0; i < id17.length(); i++) {
            sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
        }
        mode = sum % 11;
        return validate[mode];
    }
}
