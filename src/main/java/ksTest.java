import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ksTest {
    public static void main(String[] args) {
        //倒序输出
        /*String a="abcde";
        StringBuffer buffer = new StringBuffer(a);
        System.out.println(buffer.reverse());*/
        //取出邮箱地址@以前的部分，然后把数字删掉 youxiang667788@163.com
       /* String email ="youxiang667788@163.com";
        int indexs = email.indexOf("@");
        String ss = email.substring(0,indexs);
        System.out.println(ss.replaceAll("[^(A-Za-z)]",""));*/

       //取出100以内不能被3整除的正整数
     /*   for (int i=1;i<=100;i++){
            int j = i%3;
            if (j!=0){
                System.out.println(i);
            }
        }*/


    }
}
