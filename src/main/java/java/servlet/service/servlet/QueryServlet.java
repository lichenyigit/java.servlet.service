package java.servlet.service.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shihou.summer.activities.bean.Page;
import com.shihou.summer.activities.exception.base.BaseException;
import com.shihou.summer.activities.service.ForumInfoService;
import com.shihou.summer.activities.servlet.base.BaseGetServlet;
import com.shihou.summer.activities.util.ConvertUtil;
import com.shihou.summer.activities.util.dictionnary.AuditStatusEnum;
import com.shihou.summer.activities.util.dictionnary.EnableEnum;
import com.shihou.summer.activities.util.dictionnary.OrderByTypeEnum;

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
