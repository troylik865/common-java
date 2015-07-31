<%@include file="/WEB-INF/views/include/var.jsp" %>
<script type="text/javascript">
$(function(){
	var trs = $("tr");
	$.each(trs,function(i,tr){
		if($(tr).context.attributes.ondblclick){
			$(tr).css("cursor","pointer");
		} 
	});
});
</script>
