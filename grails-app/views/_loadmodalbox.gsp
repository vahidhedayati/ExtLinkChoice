<div class='modal fade' id="modalBox" tabindex='-1' role='dialog' aria-labelledby="modalLabel" aria-hidden='true'>
  <div class="modal-dialog" style="width:100%;height:100%; ">
      <div class="modal-content">
	<div class='modal-header'>
		<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>
		<div id='myModalLabel'><h2>${attrs?.title}</h2><h3>${attrs?.description}</h3></div>
	</div>
	<div class="modal-body" >

	</div>
	</div>
	</div>
</div>
		
			
<g:javascript>
	$('a.btn').on('click', function(e) {
		e.preventDefault();
		var url = $(this).attr('href');
		var title=$(this).attr('title');
		var desc=$(this).attr('description');
		var close=$(this).attr('close');
		var heading='<button type=\'button\' class=\'close\' data-dismiss=\'modal\' aria-hidden=\'true\'>${attrs?.close}</button>'
		var heading1='<div id=\'myModalLabel\'><h2>'+title+'</h2><h3>'+desc+'</h3></div>'
 		$(".modal").css('position','${attrs.position}');
  		$(".modal").css('top','${attrs.top}');
  		$(".modal").css('margin-top','${attrs.margintop}');
  		$(".modal").css('left','${attrs.left}');
  		$(".modal").css('right','${attrs.right}');
		$(".modal").css('margin-right','${attrs.marginright}');
		$(".modal").css('height',$( window ).height()${attrs.calctype}${attrs.height });
		$(".modal").css('width',$( window ).width()${attrs.calctype}${attrs.width });
		$(".modal").css('overflow','${attrs.overflow}');
		$(".modal-body").css('height',$( window ).height()${attrs.calctype}${attrs.bodyheight });
		$(".modal-body").css('width','${attrs.bodywidth}');
		$(".modal-header").html(heading+''+heading1);
		$(".modal-body").html('<iframe style="zoom=${attrs.iframezoom };width: ${attrs.iframewidth }; height: ${attrs.iframeheight }; margin:${attrs.iframemargin }; padding:${attrs.iframepadding };" frameborder="0" scrolling="auto" allowtransparency="${attrs.iframetransparency }" src="'+url+'"></iframe>');
	});
	
</g:javascript>
