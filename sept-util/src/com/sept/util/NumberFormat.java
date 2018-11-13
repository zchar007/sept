package com.sept.util;

import java.text.DecimalFormat;
import java.util.Random;

import com.sept.exception.ApplicationException;

/**
 * 还属于测试中的类 银行、单位和个人填写的各种票据和结算凭证是办理支付结算和现金收付的重要依据，直接关系到支付结算的准确、及时和安全。票据和结算凭证是银行、
 * 单位和个人凭以记载账务的会计凭证
 * ，是记载经济业务和明确经济责任的一种书面证明。因此，填写票据和结算凭证必须做到标准化、规范化、要素齐全、数字正确、字迹清晰、不错漏、不潦草、防止涂改。
 * 中文大写金额数字应用正楷或行书填写
 * ，如壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿、元、角、分、零、整(正)等字样，不得用一、二(两)、三、四、五
 * 、六、七、八、九、十、毛、另(或0)填写，不得自造简化字。如果金额数字书写中使用繁体字，如贰、陆、亿、万、圆的，也应受理。
 * 人民币大写的正确写法还应注意以下几项：
 * 一、中文大写金额数字到“元”为止的，在“元”之后、应写“整”(或“正”)字；在“角”之后，可以不写“整”(或“正”)
 * 字；大写金额数字有“分”的，“分”后面不写“整”(或“正”)字。
 * 二、中文大写金额数字前应标明“人民币”字样，大写金额数字应紧接“人民币”字样填写，不得留有空白
 * 。大写金额数字前未印“人民币”字样的，应加填“人民币”三字，在票据和结算凭证大写金额栏内不得预印固定的“仟、佰、拾、万、仟、佰、拾、元、角、分”字样。
 * 三、阿拉伯数字小写金额数字中有“0”时，中文大写应按照汉语语言规律、金额数字构成和防止涂改的要求进行书写。举例如下：
 * 1、阿拉伯数字中间有“0”时，中文大写要写“零”字，如￥1409.50应写成人民币壹仟肆佰零玖元伍角；
 * 2、阿拉伯数字中间连续有几个“0”时、中文大写金额中间可以只写一个“零”字，如￥6007.14应写成人民币陆仟零柒元壹角肆分。
 * 3、阿拉伯金额数字万位和元位是
 * “0”，或者数字中间连续有几个“0”，万位、元位也是“0”但千位、角位不是“0”时，中文大写金额中可以只写一个零字，也可以不写
 * “零”字，如￥1680.32应写成人民币壹仟陆佰捌拾元零叁角贰分
 * ，或者写成人民币壹仟陆佰捌拾元叁角贰分。又如￥107000.53应写成人民币壹拾万柒仟元零伍角叁分，或者写成人民币壹拾万零柒仟元伍角叁分。
 * 4、阿拉伯金额数字角位是
 * “0”而分位不是“0”时，中文大写金额“元”后面应写“零”字，如￥16409.02应写成人民币壹万陆仟肆佰零玖元零贰分，又如￥325
 * .04应写成人民币叁佰贰拾伍元零肆分。 四、阿拉伯小写金额数字前面均应填写人民币符号“￥”，阿拉伯小写金额数字要认真填写，不得连写分辨不清。
 * 五、票据的出票日期必须使用中文大写
 * ，为防止变造票据的出票日期，在填写月、日时、月为壹、贰和壹拾的，日为壹至玖和壹拾、贰拾和叁拾的，应在其前加“零”，日为拾壹至拾玖的应在其前加
 * “壹”，如1月15日应写成零壹月壹拾伍日，再如10月20日应写成零壹拾月零贰拾日。
 * 六、票据出票日期使用小写填写的，银行不予受理；大写日期未按要求规范填写的，银行可予受理，但由此造成损失的由出票人自行承担。
 * 
 * @author 张超
 * @version 1.0 创建时间 2017-5-27
 */
@Deprecated
public class NumberFormat {
	private static final char[] CHINESE_NUMBER = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
	private static final char[] CHINESE_CODE = { '角', '分', '整' };
	private static final char[] CHINESE_CARRY = { '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '兆', '拾',
			'佰', '仟', '万', '拾', '佰', '仟' };

	public static void main(String[] args) throws ApplicationException {
		// String str = "11118200000.34";
		// System.out.println(formatToChinese(str));
		// System.out.println(DataFormat.numberToChinese(11118200000.34));
		// 壹拾兆仟佰捌拾柒亿陆仟零佰拾贰万零仟佰拾元零角分
		System.out.println(formatToChinese("736900400.01"));
		System.out.println(DataFormat.numberToChinese(736900400.01));
		Random random = new Random();
		for (int i = 0; i < 999999999; i++) {
			double d = (double) random.nextInt(999999999) + random.nextInt(100) * 0.01;
			String str = DecimalFormat.getInstance().format(d);
			String result = str.replaceAll(",", "");
			String s1 = formatToChinese(result);
			String s2 = DataFormat.numberToChinese(d);
			if (!s1.equals(s2)) {
				System.out.println(result + " : mine--" + s1 + "  othe--" + s2);
			}
			// System.out.println(formatToChinese("1007800120010.00"));
			// System.out.println(DataFormat.numberToChinese(1007800120010.00));
		}
		System.out.println("over");

		// String chineseStr = "壹拾零兆零仟柒佰捌拾柒亿陆仟零佰壹拾贰万零仟零佰零拾玖元零角零分";
		// 壹拾零兆零柒佰捌拾柒亿陆仟零壹拾贰万零零零玖元零零分

		// chineseStr =
		// chineseStr.replaceAll("(?<=((\\w|[\\u4e00-\\u9fa5])零))([^(亿|万|兆)])(?=(\\w|[\\u4e00-\\u9fa5]))",
		// "");
		// System.out.println(chineseStr);

	}

	public static String formatToChinese(double vMoney) throws ApplicationException {
		return formatToChinese(Double.toString(vMoney));
	}

	public static String formatToChinese(String vMoney) throws ApplicationException {
		// 是不是数字格式,不是则直接报错
		Double.parseDouble(vMoney);
		String zheng = vMoney;
		String xiao = "";
		// 如果钱数含有小数点,则
		if (vMoney.indexOf('.') > 0) {
			String[] strTemp = vMoney.split("\\.");
			zheng = strTemp[0];
			xiao = strTemp[1];
		}
		if (zheng.length() > CHINESE_CARRY.length || xiao.length() > 2) {
			throw new ApplicationException("数额太大或小数太多:" + vMoney);
		}
		String chineseStr = "";
		int carry = zheng.length() - 1;
		for (int i = 0; i < zheng.length(); i++) {
			chineseStr += (CHINESE_NUMBER[Integer.parseInt(zheng.charAt(i) + "")]) + (CHINESE_CARRY[carry - i] + "");
		}
		// if(xiao.length()>0){
		// chineseStr = chineseStr.substring(0,chineseStr.length()-1);
		// }
		for (int i = 0; i < xiao.length(); i++) {
			chineseStr += (CHINESE_NUMBER[Integer.parseInt(xiao.charAt(i) + "")]) + (CHINESE_CODE[i] + "");
		}

		/**
		 * 1.去掉除万,亿,兆,元之外所有数值为0 的位 2.整合多个零的为*(或者直接转成零,去除4,5步骤) 3,去掉独立的零 4.恢复由多个零整合而成的零
		 * 5.去掉亿,兆,万,元前边的零 6.去除前后的0 7.如果以元结尾,则加上整,8.删除关键位数相连
		 */
		// System.out.println(chineseStr);
		// 1.去掉除万,亿,兆之外所有数值为0 的位
		chineseStr = chineseStr
				.replaceAll("(?<=((\\w|[\\u4e00-\\u9fa5])零))([^(亿|万|兆|元)])(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
		// System.out.println(chineseStr);
		// 2.整合多个零的为*
		chineseStr = chineseStr.replaceAll("零{2,}", "零");
		// 3,去掉独立的零
		// chineseStr = chineseStr.replaceAll("零", "");
		// 4.恢复由多个零整合而成的零
		// chineseStr = chineseStr.replaceAll("\\*", "零");
		// 5.去掉亿,兆,万,元前边的零
		chineseStr = chineseStr.replaceAll("(零)(?=(兆|亿|万|元)(\\w|[\\u4e00-\\u9fa5]){0,})", "");
		// 6.去除前后的0
		while (chineseStr.startsWith("零")) {
			chineseStr = chineseStr.substring(2, chineseStr.length());
		}
		while (chineseStr.endsWith("零")) {
			chineseStr = chineseStr.substring(0, chineseStr.length() - 1);
		}
		// 7.如果以元结尾,则加上整
		if (chineseStr.endsWith("元")) {
			chineseStr += "整";
		} else {
			// 把十万元，百万元，千万元的元去掉
			chineseStr = chineseStr.replaceAll("(?<=(拾万|佰万|仟万))(元)(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
			// chineseStr = chineseStr.replaceAll("拾万元", "拾万");
			// chineseStr = chineseStr.replaceAll("佰万元", "佰万");
			// chineseStr = chineseStr.replaceAll("仟万元", "仟万");
		}
		while (chineseStr.startsWith("元")) {
			chineseStr = chineseStr.substring(1);
		}

		// 8.删除关键位数相连
		chineseStr = chineseStr.replaceAll("(?<=(兆|亿))(亿|万)(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
		// 删除前边是兆亿万,后边是零的元,这个是为了配合框架,结果发现框架的规律琢磨不清,加不加元的数没有明显的不同
		// chineseStr =
		// chineseStr.replaceAll("(?<=(兆|亿|万))(元)(?=((\\w|[\\u4e00-\\u9fa5])){1,})","");

		// 还需要删除分前边的零

		while (chineseStr.startsWith("零")) {
			chineseStr = chineseStr.substring(1);
		}
		while (chineseStr.endsWith("零")) {
			chineseStr = chineseStr.substring(0, chineseStr.length() - 1);
		}
		return chineseStr;

	}

}
