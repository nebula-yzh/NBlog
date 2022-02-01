package top.naccl.service;

import java.util.Map;

/**
 * @author 应志豪
 * @date 2022/1/31
 * @Description: 关于我的service
 */
public interface AboutService {

	/**
	 * 获取关于我的信息
	 * @return 返回map
	 */
	Map<String, String> getAboutInfo();

	/**
	 * 获取关于我的设置
	 * @return 返回map
	 */
	Map<String, String> getAboutSetting();

	void updateAbout(Map<String, String> map);

	boolean getAboutCommentEnabled();
}
