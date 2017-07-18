package java.servlet.service.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.servlet.service.exception.base.BaseException;
import java.servlet.service.service.ForumInfoService;
import java.servlet.service.servlet.base.BasePostServlet;
import java.servlet.service.util.ConvertUtil;

/**
 * 添加评论
 * @author lichenyi
 * @date 2017-7-18 11:40
 */
@WebServlet(value = "/forum/add")
public class AddServlet extends BasePostServlet<Boolean> {
                                                                                 
    @Override
    protected Boolean processPost(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        String uid = ConvertUtil.getNonEmptyTrimStringFromRequestParam(request, "uid");
        String content = ConvertUtil.getNonEmptyTrimStringFromRequestParam(request, "content");
        return ForumInfoService.add(uid, content);
    }
}
