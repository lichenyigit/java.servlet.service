package java.servlet.service.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shihou.summer.activities.exception.base.BaseException;
import com.shihou.summer.activities.service.ForumInfoService;
import com.shihou.summer.activities.servlet.base.BasePostServlet;
import com.shihou.summer.activities.util.ConvertUtil;

/**
 * 点赞接口
 * @author lichenyi
 * @date 2017-7-18 11:40
 */
@WebServlet(value = "/forum/fabulous")
public class FabulousServlet extends BasePostServlet<Boolean> {
    private static final long serialVersionUID = 1L;

	@Override
    protected Boolean processPost(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        String id = ConvertUtil.getNonEmptyTrimStringFromRequestParam(request, "id");
        String uid = ConvertUtil.getNonEmptyTrimStringFromRequestParam(request, "uid");
        String toUid = ConvertUtil.getNonEmptyTrimStringFromRequestParam(request, "toUid");
        return ForumInfoService.updateFabulous(id, uid, toUid);
    }
}
