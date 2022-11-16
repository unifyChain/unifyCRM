function handleSubmit(args, dialog) {
	var jqDialog = jQuery('#' + dialog);
	if (args.validationFailed) {
		jqDialog.effect('shake', {
			times : 3
		}, 100);
	} else {
		PF(dialog).hide();
	}
}
function test(lng, lat) {

	for (var i = 0; i < 5; i++) {
		var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7),
				ne.lat - latSpan * (Math.random() * 0.7));
		var marker = new BMap.Marker(point);

		var label = new BMap.Label("云停车" + i, {
			offset : new BMap.Size(20, -10)
		});
		marker.setLabel(label);
		map.addOverlay(marker);
		// alert("云停车" + i);
		addClickHandler("云停车" + i, marker);

	}

}

function ry(data_info) {
	for (var i = 0; i < data_info.length; i++) {
		var myIcon = new BMap.Icon("images/ry.png", new BMap.Size(150, 80));

		var marker = new BMap.Marker(new BMap.Point(data_info[i][0],
				data_info[i][1]), {
			icon : myIcon
		});

		var label = new BMap.Label(data_info[i][2], {
			offset : new BMap.Size(20, -10)
		});
		marker.setLabel(label);
		map.addOverlay(marker);
	}

}

function sb(data_info) {
	for (var i = 0; i < data_info.length; i++) {
		var myIcon = new BMap.Icon("images/sb.png", new BMap.Size(150, 80));

		var marker = new BMap.Marker(new BMap.Point(data_info[i][0],
				data_info[i][1]), {
			icon : myIcon
		});

		var label = new BMap.Label(data_info[i][2], {
			offset : new BMap.Size(20, -10)
		});

		marker.setLabel(label);
		map.addOverlay(marker);
	}

}

function pqbw(data) {

	$.ajax({
		type : "get",
		url : "http://localhost:8080/tingzheer/services/cloudpark/tcs/110",
		dataType : "json",
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				// alert( data[i].id);

			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}
function bwditu(data) {
	for (var i = 0; i < data.length; i++) {

		var point = new BMap.Point(data[i].jd, data[i].wd);
		var marker = new BMap.Marker(point);
		var lb = "";
		if (data[i].id == "da3a7f7a-5a81-4be7-82df-7b94a87c9301") {
			lb = data[i].mc + " 总：36 空：2";
		} else {
			lb = data[i].mc + " 总：52 空：8";
		}

		var label = new BMap.Label(lb, {
			offset : new BMap.Size(20, -10)
		});
		alert("aa:" + lb + data[i].id);
		marker.setLabel(label);
		map.addOverlay(marker);

		addClickHandlers(data[i].id, marker);
	}
}

function bwditu1(data){
	
	for (var i = 0; i < data.length; i++) {
		
		var point = new BMap.Point(data[i].jd,
				data[i].wd);
		var marker = new BMap.Marker(point);
		var lb=data[i].mc+" 总："+data[i].bws+" 空："+data[i].kbws;
		
		var label = new BMap.Label(lb, {
			offset : new BMap.Size(20, -10)
		});
		alert("aa:"+lb+data[i].jd+":"+data[i].wd);
		marker.setLabel(label);
		map.addOverlay(marker);
		
		addClickHandler(data[i].id,marker);
	}
}
function addClickHandlers(content, marker) {
	marker.addEventListener("click", function(e) {
		window.location.href = "ldxxs1.jsp?id=" + content;
	});
}

function kshry(data){
	for (var i = 0; i < data.length; i++) {
		
		
		var myIcon = new BMap.Icon("images/ry.png", new BMap.Size(150, 80));

		var marker = new BMap.Marker(new BMap.Point(data[i].jd,
				data[i].wd), {
			icon : myIcon
		});
		var lb="编号:"+data[i].username+" 姓名:"+data[i].firstName;
		var label = new BMap.Label(lb, {
			offset : new BMap.Size(20, -10)
		});
		marker.setLabel(label);
		map.addOverlay(marker);
		
	}
	
	
}

function kshzb(data){
	for (var i = 0; i < data.length; i++) {
		
		
		var myIcon = new BMap.Icon("images/sb.png", new BMap.Size(150, 80));

		var marker = new BMap.Marker(new BMap.Point(data[i].jd,
				data[i].wd), {
			icon : myIcon
		});
		var lb="编号:"+data[i].id;
		var label = new BMap.Label(lb, {
			offset : new BMap.Size(20, -10)
		});
		marker.setLabel(label);
		map.addOverlay(marker);
		
	}
	
	
}



function bwdt(data) {
	for (var i = 0; i < data.length; i++) {

		var point = new BMap.Point(data[i].jd, data[i].wd);
		var marker = new BMap.Marker(point);
		var lb = "";
		if (data[i].id == "b2a036e4-9c66-4678-b563-ed60b9fe5a29") {
			lb = data[i].mc + " 总：36 空：2";
		} else {
			lb = data[i].mc + " 总：52 空：8";
		}

		var label = new BMap.Label(lb, {
			offset : new BMap.Size(20, -10)
		});
		//alert("aa:" + lb + data[i].id);
		marker.setLabel(label);
		map.addOverlay(marker);

		addClickHandler(data[i].id, marker);
	}
}
function addClickHandler(content, marker) {
	marker.addEventListener("click", function(e) {
		window.location.href = "ldxx1.jsp?id=" + content;
	});
}
function openInfo(content, e) {
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
	map.openInfoWindow(infoWindow, point); // 开启信息窗口
}
PrimeFaces.locales['zh_CN'] = {
	    closeText: '关闭',
	    prevText: '上个月',
	    nextText: '下个月',
	    currentText: '今天',
	    monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
	    monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
	    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
	    dayNamesShort: ['日','一','二','三','四','五','六'],
	    dayNamesMin: ['日','一','二','三','四','五','六'],
	    weekHeader: '周',
	    firstDay: 1,
	    isRTL: false,
	    showMonthAfterYear: true,
	    yearSuffix: '', // 年
	    timeOnlyTitle: '仅时间',
	    timeText: '时间',
	    hourText: '时',
	    minuteText: '分',
	    secondText: '秒',
	    ampm: false,
	    month: '月',
	    week: '周',
	    day: '日',
	    allDayText : '全天'
	};