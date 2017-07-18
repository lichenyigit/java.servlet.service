package java.servlet.service.servlet;

import java.servlet.service.bean.Page;
import java.servlet.service.exception.base.BaseException;
import java.servlet.service.service.ForumInfoService;
import java.servlet.service.servlet.base.BaseGetServlet;
import java.servlet.service.util.ConvertUtil;
import java.servlet.service.util.dictionnary.AuditStatusEnum;
import java.servlet.service.util.dictionnary.EnableEnum;
import java.servlet.service.util.dictionnary.OrderByTypeEnum;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 更新评论
 * @author lichenyi
 * @date 2017-7-18 11:40
 */
@WebServlet(value = "/forum/query")
public class QueryServlet extends BaseGetServlet<Page<Map<String, Object>>> {
    private static final long serialVersionUID = 1L;

	@Override
    protected Page<Map<String, Object>> processGet(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        Long pageSize = ConvertUtil.getLongFromRequestParam(request, "pageSize", 10l);
        Long pageNum = ConvertUtil.getLongFromRequestParam(request, "pageNum", 0L);
        String uid = ConvertUtil.getTrimStringFromRequestParam(request, "uid", null);
        String content = ConvertUtil.getTrimStringFromRequestParam(request, "content", null);
        AuditStatusEnum auditStatus = ConvertUtil.getAuditStatus(request, "auditStatus");
        EnableEnum enable = ConvertUtil.getEnableEnum(request, "enable");
        OrderByTypeEnum orderType = ConvertUtil.getOrderByTypeEnum(request, "orderType");
        return ForumInfoService.queryList(uid, content, auditStatus, enable, orderType, pageSize, pageNum);
    }
}
