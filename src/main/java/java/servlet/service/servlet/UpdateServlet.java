package java.servlet.service.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.servlet.service.exception.base.BaseException;
import java.servlet.service.service.ForumInfoService;
import java.servlet.service.servlet.base.BasePostServlet;
import java.servlet.service.util.ConvertUtil;
import java.servlet.service.util.dictionnary.AuditStatusEnum;
import java.servlet.service.util.dictionnary.EnableEnum;

/**
 * 更新评论
 * @author lichenyi
 * @date 2017-7-18 11:40
 */
@WebServlet(value = "/forum/update")
public class UpdateServlet extends BasePostServlet<Boolean> {
    private static final long serialVersionUID = 1L;

	@Override
    protected Boolean processPost(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        String id = ConvertUtil.getNonEmptyTrimStringFromRequestParam(request, "id");
        //String fabulousCount = ConvertUtil.getTrimStringFromRequestParam(request, "fabulousCount", "0");
        String fabulousCount = "0";
        AuditStatusEnum auditStatus = ConvertUtil.getAuditStatus(request, "auditStatus");
        EnableEnum enable = ConvertUtil.getEnableEnum(request, "enable");
        return ForumInfoService.update(id, fabulousCount, auditStatus, enable);
    }
}
