package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import utils.VelocityUtil;

public class PageScriptTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String column="";
	private String title="";
	private String height="100%";
	private String width="100%";
	private String pkey="";
	private String editUrl="";
	private String editable="";
	private String pageUrl="";
	private String deleteUrl="";
	private String searchUrl="";
	private String adSearchUrl="";
	private String previewUrl="";
	private String addUrl="";
	private String pageStyle="2";
	private String pageNum="10";
	private String previewStyle="";
	private String rows="15";
	private String id="";
	private String exfn="";
	private String recycleUrl="";
	private String cookie="false";

	public String getExfn() {
		return exfn;
	}

	public void setExfn(String exfn) {
		this.exfn = exfn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}




	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getAddUrl() {
		return addUrl;
	}

	public void setAddUrl(String addUrl) {
		this.addUrl = addUrl;
	}

	public String getPageStyle() {
		return pageStyle;
	}

	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPreviewStyle() {
		return previewStyle;
	}

	public void setPreviewStyle(String previewStyle) {
		this.previewStyle = previewStyle;
	}



	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	
	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public int doStartTag() throws JspException {
		try {
			JspWriter out=pageContext.getOut();
			Template template = VelocityUtil.getTemplate("tags/pageScriptTag.vm");
			VelocityContext ctx = VelocityUtil.getVelocityContext();
			ctx.put("ctx", pageContext.getServletContext().getContextPath());
			ctx.put("title", title);
			ctx.put("height", height);
			ctx.put("width", width);
			ctx.put("pKey", pkey);
			ctx.put("editable", editable);
			ctx.put("editUrl", editUrl);
			ctx.put("pageUrl", pageUrl);
			ctx.put("deleteUrl", deleteUrl);
			ctx.put("searchUrl", searchUrl);
			ctx.put("previewUrl", previewUrl);
			ctx.put("previewStyle", previewStyle);
			ctx.put("pageNum", pageNum);
			ctx.put("addUrl", addUrl);
			ctx.put("adSearchUrl", adSearchUrl);
			ctx.put("pageStyle", pageStyle);
			ctx.put("rows", rows);
			ctx.put("id", id);
			ctx.put("column", column);
			ctx.put("exfn", exfn);
			ctx.put("cookie", cookie);
			ctx.put("recycleUrl", recycleUrl);
			String info = VelocityUtil.getTemplateInfo(template, ctx);
			out.println(info);
		} catch (Exception e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
	  
   public int doEndTag() throws javax.servlet.jsp.JspException {
		return EVAL_BODY_AGAIN;
	}

	public void release() {
		super.release();
	}

	public String getAdSearchUrl() {
		return adSearchUrl;
	}

	public void setAdSearchUrl(String adSearchUrl) {
		this.adSearchUrl = adSearchUrl;
	}

	public String getRecycleUrl() {
		return recycleUrl;
	}

	public void setRecycleUrl(String recycleUrl) {
		this.recycleUrl = recycleUrl;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	} 
	
	


}
