package com.yingqida.richplay.baseapi.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static Regex match(String content) {
		Regex result = null;
		if (null == content || "".equals(content))
			return result;
		Matcher matcher = Pattern.compile(
				"(<[a-z][_][a-z].*>.*</[a-z][_][a-z]>)").matcher(content);

		String ret = null;
		int lastIdx = 0;
		while (matcher.find()) {
			if (null == result)
				result = new Regex();
			ret = matcher.group();
			String tag = null;
			if (ret.indexOf(Regex.TAG_A) != ret.lastIndexOf(Regex.TAG_A)) {
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_A);
				tag = Regex.TAG_A;
			} else if (ret.indexOf(Regex.TAG_D) != ret.lastIndexOf(Regex.TAG_D)) {
				tag = Regex.TAG_D;
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_D);
			} else if (ret.indexOf(Regex.TAG_F) != ret.lastIndexOf(Regex.TAG_F)) {
				tag = Regex.TAG_F;
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_F);
			} else if (ret.indexOf(Regex.TAG_N) != ret.lastIndexOf(Regex.TAG_N)) {
				tag = Regex.TAG_N;
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_N);
			} else if (ret.indexOf(Regex.TAG_P) != ret.lastIndexOf(Regex.TAG_P)) {
				tag = Regex.TAG_P;
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_P);
			} else if (ret.indexOf(Regex.TAG_S) != ret.lastIndexOf(Regex.TAG_S)) {
				tag = Regex.TAG_S;
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_S);
			} else if (ret.indexOf(Regex.TAG_V) != ret.lastIndexOf(Regex.TAG_V)) {
				tag = Regex.TAG_V;
				// result = new Regex(content.replace(ret, ""), ret,
				// Regex.TAG_S);
			}
			if (tag != null) {
				int curIdx = content.indexOf(ret);
				if (curIdx != lastIdx) {
					Rcontent retc = new Rcontent();
					retc.content = content.substring(lastIdx, curIdx);
					result.contents.add(retc);
				}
				lastIdx = curIdx + ret.length();
				Rcontent rc = new Rcontent();
				rc.isTag = true;
				rc.content = ret;
				rc.tag = tag;
				result.contents.add(rc);
			}
			// if (null != result)
			// {
			// Matcher match = Pattern.compile(">.*<").matcher(ret);
			// if (match.find())
			// {
			// String group = match.group();
			// result.regexTagContent = group.substring(1,
			// group.length() - 1);
			// result.content = content.replace(ret, String.format(
			// "<font color=\"#ff3366\">%s</font>",
			// result.regexTagContent));
			// }
			// else
			// {
			// result = null;
			// }
			// }
		}
		if (ret != null) {
			int idx = content.indexOf(ret);
			if (idx + ret.length() != content.length()) {
				Rcontent retc = new Rcontent();
				retc.content = content.substring(idx + ret.length());
				result.contents.add(retc);
			}
		} else {
			result = null;
		}
		return result;
	}
}
