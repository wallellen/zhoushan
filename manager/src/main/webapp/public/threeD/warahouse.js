/**
 * 仓库绘制 
 *  obj模型导入
 *  创建的仓库的id，及其所属的公司id , 当前仓库的功能级别
 */
var Warahouse = function ( scale , level) {

  	var stats;

	var camera, scene, renderer ,loader , controls;
	var container;

	var SCREEN_HEIGHT ;
    var SCREEN_WIDTH ;

    //ray投影
    var raycaster = new THREE.Raycaster();
    var projector = new THREE.Projector();
    var directionVector = new THREE.Vector3();

    var clickInfo = {
     		x: 0,
      	    y: 0,
      	    userHasClicked: false ,
      	    dbClicked : false 
    };


    //var statsNode = document.getElementById('stats');

    //点击鼠标标志
    var marker;
    //偏移修正
  	var numx = 0,numy = 0;

    //物体类目信息
  	//保存仓库信息
    var warahouse = { 
    	scale : ( scale != undefined ) ? scale : 0.5 ,
    	level : ( level != undefined ) ? level : 15 ,
        x_l : 300 ,
        z_l : 200 ,
        y_l : 135 ,
        x_inter : 0 , 
        y_inter : 0 
        
    };

    var reader = {};
    var sensor = {};
  	

    //绘制场景
  	var drawScene = function ( domElement ) {

  		container = domElement ;
		SCREEN_WIDTH =  container.clientWidth;
		SCREEN_HEIGHT =  container.clientHeight;
				
		
		camera = new THREE.PerspectiveCamera( 45, SCREEN_WIDTH /SCREEN_HEIGHT, 1, 2000 );
		camera.position.x = 10;
		camera.position.y = 300;
		camera.position.z = -450;

		// These variables set the camera behaviour and sensitivity.
		controls = new THREE.OrbitControls( camera , container );
		controls.maxDistance = 1000;
		controls.maxPolarAngle = Math.PI / 3;
      	

		// scene
        scene = new THREE.Scene();

		var ambient = new THREE.AmbientLight( 0x404040 );
		//scene.add( ambient );
		
		//lights here
		var light1 = new THREE.DirectionalLight( 0xffffff , 0.6);
		light1.position.set( 26, 30, -35 );   
		scene.add( light1 );
		
		var light2 = new THREE.DirectionalLight( 0xffffff , 0.5);
		light2.position.set( -24, 30, -35 );
		scene.add( light2 );


  		//loader
        loader = new THREE.OBJMTLLoader();
	   

		marker = new THREE.Mesh(new THREE.SphereGeometry(1), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
       	//scene.add(marker);

  		var parent = container; 
  		while(parent){
    		numx += parent.offsetLeft;
   			numy +=parent.offsetTop;
    		parent = parent.offsetParent;
 		}

		renderer = new THREE.WebGLRenderer();
		renderer.setSize( SCREEN_WIDTH , SCREEN_HEIGHT );
		container.appendChild( renderer.domElement );

		THREEx.FullScreen.bindKey({ element : container});

  	};
    
    //仓库相关函数 START
    //绘制仓库
  	var drawWarahouse = function () {


  		 loader.load( '../public/models/storage_6.obj', '../public/models/storage_6.mtl', function ( object ) 
  		{

 			object.position.x = -250;
 			object.position.y = 0;
 			object.position.z = -100;

 			object.scale.x=object.scale.y=object.scale.z= 0.5;

 			object.name = 'storage';
 			warahouse.id3d = object.id;

 			scene.add( object );
 			
 			//object.addEventListener( 'click' , onClick);
 			
         } );

        //保存当前仓库的基本信息


  	};

  	//不需要 vm上已经用storage 以及 prolist 存储了仓库以及货物的信息。
    //获取仓库信息，仓库大小等
  	var getWarahouseInfo = function () {
         
  		
  		// ajax请求后台当前仓库信息.
  		jQuery.ajax({
			type:'POST',
			url:'product?action=query',
			success:function(data){
			    //alert("查询成功");
			    var waraInfo = JSON.parse(data);
			    warahouse.prolist = waraInfo.prolist;
			    warahouse.storage = waraInfo.storage;
			},
            async:true
		}); 
      // return  waraInfo;
  	};

  	//仓库相关函数 END


    //货物相关函数 Start
  	
   var getBoxInfo = function () {
         
  		var proInfo ;
  		// ajax请求后台当前仓库信息.
  		jQuery.ajax({
			type:'POST',
			url:'inventory?action=query',
			success:function(data){
			    //alert("查询成功");
				proInfo = JSON.parse(data).proList;
				//console.log(proInfo);
			},
            async:false
		}); 
  		
       return  proInfo;
  	};
  	   	
  	var drawBox = function ( proList ) {

  		//绘制货物的时候要计算仓库的长宽等限制
       
  		var proList = getBoxInfo();
  		var boxCount = 6;

  		loader.load( '../public/models/box.obj', '../public/models/box.mtl', function ( objectProto )
  		{
  			//计算仓库存放货物的数目
  			warahouse.x_inter = 75 * warahouse.scale - 2.5;
  	  		warahouse.y_inter = 90 * warahouse.scale;
  	  		warahouse.z_inter = 10 + 50 * warahouse.scale ;
  	  		
  	  	    objectProto.name = "box";
  	  		
  	  		//x 最多box数目
  	  		var x_max = warahouse.x_l / warahouse.x_inter;
  	  		//console.log( x_max );
  	  		//y 最多Box数目
  	  		var y_max = warahouse.y_l / warahouse.y_inter;
  	  		
  	  		for ( var i = proList.length -1 , x_index = 0 , y_index = 0 , z_index = 0 ; i>=0 ;i--){
  	  			
  	  			var pro = proList[i];
  	  			var boxCount = pro.count / pro.unit;
  	  			
  	  			//当前货物无库存，跳过
  	  			if ( boxCount == 0 )
  	  				continue;
  	  			
  	  			//写入当前货物的Object中
  	  		    objectProto.userData = {
  	  		    	proInfo : pro,
  	  		    	color : Math.random() * 0xffffff	    
  	  		    };
  		   
  	  		  	//绘制该货物
  	  		    boxCount = 1;
  	  			for ( var j = 0  ; j < boxCount ; j++ ){
  	  				
  	  				var object = objectProto.clone();
  	  			    //console.log( object.userData.color );
  	  			    //change the color here
  	  			    //failed 
  	  				console.log( object );
  	  			   
  	  			    /*object.traverse ( function (child){
  	  			    	if( child instanceof THREE.Mesh)
  	  			    		child.material.color.setHex( object.userData.color  );
  	  			    } );*/
  	  			    
  	  			    
  	  			    object.position.x =  140 - 500 * warahouse.scale - x_index * ( warahouse.x_inter ) ;
	  			    object.position.y = 0 + y_index * warahouse.y_inter;
	  			    object.position.z = 100 - 200 * warahouse.scale - z_index * warahouse.z_inter;
	  			    
	  			    object.scale.x=object.scale.y=object.scale.z= warahouse.scale;
	  			    
	  			    scene.add(object);
  	  			    
  	  				
  	  				//计算x,j,z位移系数
  	  				if ( (x_index + 1) >= x_max ){
  	  					// x 换行
  	  					if ( y_index >= y_max ){
  	  						// y 满行 , z轴平铺
  	  						z_index++;
  	  						x_index = 0;
  	  						y_index = 0;
  	  					}
  	  					else{
  	  						y_index++;
  	  						x_index = 0;
  	  					}  					
  	  				  
  	  				}
  	  				else {
  	  					x_index ++;
  	  				}
  	  				 	  			    
  	  			}
  	  				
  	  		}
  	  		
   		});
  				  
         
  	};
  	
  	//flag:true 显示Box  flase：不显示box
  	var toggleBox = function ( flag ){
  		
  		var boxes = getObjectsByName('box');
  		
  		if( boxes.length == 0  && flag ){
  			drawBox();
  		}
  		else {
  			for ( var i = boxes.length -1 ; i>=0 ;i--){
  				boxes[i].traverse( function ( object ) { object.visible = flag; } );
  			}
  		}
  	} ;
  	
  	
  
  	//货物相关函数 End
   
    //RFID阅读器相关函数 Start
    var drawReader = function () {
    	
    	var readerInfo = getReader();
    	
    	 loader.load( '../public/models/reader.obj', '../public/models/reader.mtl', function ( object ) 
    	{

    		 			object.position.x = -150;
    		 			object.position.y = 0;
    		 			object.position.z = -300;

    		 			object.scale.x=object.scale.y=object.scale.z= 1;
    		 			object.userData = readerInfo ;
    		 			//object.lookAt(new THREE.Vector3( -300, 0, 1000 ));
    		 			//object.rotateZ(30.0);

    		 			object.name = 'reader';
    		 			scene.add( object );
    		 			
    	 } );
    	 
    };
    
   //flag:true 显示Box  flase：不显示box
  	var switchReader = function ( flag ){
        //console.log('reader tog');
        
        
        var readers = getObjectsByName('reader');
  		
  		if( readers.length == 0  && flag ){
  			drawReader();
  		}
  		else {
  			for ( var i = readers.length -1 ; i>=0 ;i--){
  				readers[i].traverse( function ( object ) { object.visible = flag; } );
  			}
  		}
  	} ;
  	
    var getReader = function () {
    	
    	var readerInfo ;
  		// ajax请求后台当前仓库信息.
  		jQuery.ajax({
			type:'GET',
			url:'reader?action=getstate',
			success:function(data){		   
				readerInfo = JSON.parse(data);
				//console.log(readerInfo);
			},
            async:false
		}); 
  		
       return  readerInfo;

    };
    
    var setReader = function () {

    };
    //RFID阅读器相关函数 End

    //温度传感器相关函数 Start
    var drawSensor = function () {

    	var sensorCount = 1;
		loader.load( '../public/models/sensor.obj', '../public/models/sensor.mtl', function ( objectProto ) 
		{
			for (var i = sensorCount - 1; i >= 0; i--) {
				
				var object = objectProto.clone();
				//object.position.x = - 60 - i*35;
				object.position.x = -60 ;
				object.position.y = 60; 
				//object.position.z = -150 + i*40;
				object.position.z = -130 ;

				object.scale.x=object.scale.y=object.scale.z= warahouse.scale ;

				object.name = 'sensor';
				scene.add( object );

			};
		});
		
    };
 
    //温度传感器相关函数 End
   

    //绑定事件
    var addEvents = function (  ) {
    	
    	window.addEventListener('resize', handleResize, false);
        //container.addEventListener('dblclick', handleDBClick, false);
        
    };
    
   //双击转向
    var handleDBClick = function ( evt ) {
    	
       clickInfo.dbClicked = true;
   	   clickInfo.x = evt.clientX;
   	   clickInfo.y = evt.clientY;
   	   
   	   var info = render();
   	   var target = new THREE.Vector3( info.x , info.y , info.z);
   	   turnTo( target );
  	   
    }
    
   //页面缩放控制
   var  handleResize = function ( ) {
	   
	   //一定要加px进行设置
	   container.style.height= SCREEN_WIDTH+"px";
	   SCREEN_WIDTH =  container.clientWidth;
	   SCREEN_HEIGHT =  container.clientHeight;
	   
	    // update the renderer
	   renderer.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		// update the camera
	   camera.aspect = SCREEN_WIDTH / SCREEN_HEIGHT;
	   camera.updateProjectionMatrix();
   }
    
  //处理鼠标点击事件
    var handlerClick = function ( evt ) {
 	   
 	   clickInfo.userHasClicked = true;
 	   clickInfo.x = evt.clientX;
 	   clickInfo.y = evt.clientY;
 	   
 	   var info = render();
 	   
 	   if( info == undefined ){
 		   
 	   }
 	   else if( info.name == 'storage' ){
 		   //选中目标为仓库
 		   //info.detail = getWarahouseInfo();
 		   //若可以获取当前vm的变量值  $storage $prolist
 	   }
 	   else if ( info.name == 'box' ){
 		   //直接访问 userData
 		   
 	   }
 	   else if ( info.name == 'camera' ){
 		   
 	   }
 	   else if ( info.name == 'reader' ){
 		   
 	   }
 	   
 	   return info;
 	  	   
    };

   
  	return {

  		//初始化仓库主方法
  		//传参为设置的仓库规模,功能配置
  		init : function ( domElement ) {
  		
  			drawScene(domElement);
  			drawWarahouse();
  			addEvents();
  			//drawBox();
  			animate();
  		},
        
  		toggleReader : function ( flag ) {
  			switchReader( flag );
        },

        toggleBox : function ( flag ) {
            toggleBox( flag );
        },

        toggleSensor : function ( flag ) {
        	toggleSensor( flag );
        },

        getMouseClickInfo : function ( evt ) {
        	 return handlerClick( evt );
        }

  	};
  	
  	 //3d页面控制，动态渲染
   function animate () {
    	
    	requestAnimationFrame( animate );
    	
    	//turnTo( scene.position , false );
        camera.lookAt( scene.position );
		renderer.setClearColor(0xffffff);
		renderer.render( scene, camera );
    }
   
   // scene中按name属性查找object3D群组
    function getObjectsByName( name )
    {
    	var results = [];
    	for (var i = scene.children.length -1 ; i>=0 ; i--){
    	    if( scene.children[i].name === name ){
    	    	results.push(scene.children[i]);
    	    }
    	}
    	return results;
    }
    
    
    //change the camera to see the point
    function turnTo ( target ) {
    
    	camera.position.x = 0;
    	camera.position.y =  target.y ;
    	camera.position.z =  target.z;
    	
    	camera.lookAt( target );
    	controls.target = target;
    	
    	console.log('look')
    	console.log(camera);
    	console.log(target);
    	
    }

    function render() {

		if (clickInfo.userHasClicked || clickInfo.dbClicked) {

			clickInfo.userHasClicked = false;
			clickInfo.dbClicked = false;

			var top = document.documentElement.scrollTop + document.body.scrollTop;

    		var x = ( (clickInfo.x - numx)/ SCREEN_WIDTH ) * 2 - 1;
    		var y = -( (clickInfo.y - (numy - top))/ SCREEN_HEIGHT ) * 2 + 1;
    		directionVector.set(x, y, 1);
    		projector.unprojectVector(directionVector, camera);
    		directionVector.sub(camera.position);
    		directionVector.normalize();
    		raycaster.set(camera.position, directionVector);

    		var intersects = raycaster.intersectObjects(scene.children, true);
    		
    		if (intersects.length) {
      
     		var target = intersects[0].object;

     		//method 2 直接寻找父亲 注意所有物体的parent都是scene;
 		   /* while(target.parent)
 		    {
 		       if( target.parent instanceof THREE.Scene) ;
 		       else target = target.parent;
 		    }*/
 		    //设定只判断二级关系
 		    if( target.parent && !(target.parent instanceof THREE.Scene)) target = target.parent;
    		
    		return {
    			x : intersects[0].point.x,
    		    y : intersects[0].point.y,
    		    z : intersects[0].point.z,
    		    name : target.name,
    		    id : target.id,
    		    userData : target.userData
    		};
    		
    	   }

        }

	}
	
};