//This js help to turn around

var Warahouse2 = function (){
	
	var map = [ // 1  2  3  4  5  6  7  8  9
	            [1,1,1,1,0], // 0
	            [1,0,0,0,0], // 0
	            [1,0,0,0,0], // 00
	            [1,1,1,1,0] // 0
	     
	            ], mapW = map.length, mapH = map[0].length;
			
	// Semi-constants
	var WIDTH = window.innerWidth,
		HEIGHT = window.innerHeight,
		ASPECT = WIDTH / HEIGHT,
		UNITSIZE = 250,
		WALLHEIGHT = UNITSIZE / 2 ,
		MOVESPEED = 100,
		LOOKSPEED = 0.075,
		BOXGAP = 10,
		BOXUNITSIZECOUNT = 5,
		BOXSIZE = UNITSIZE / BOXUNITSIZECOUNT - BOXGAP,
		BOXHEIGHT = BOXSIZE;

	// Global vars
	var t = THREE, scene, cam, renderer, controls, domElement;
	
	//seletcted items 
	var boxHead;
	var bbHelper , selected ;
	
	//ray投影
    var raycaster = new THREE.Raycaster();
    var projector = new THREE.Projector();
    var directionVector = new THREE.Vector3();

    var clickInfo = {
     		x: 0,
      	    y: 0,
      	    userHasClicked: false 
    };
    //偏移修正
  	var numx = 0,numy = 0;

  	//model prototype
  	var modelsPros = {};

  	
	
	function getMapSector(v) {
		var x = Math.floor(v.x / UNITSIZE + mapW/2 - .5);
		var z = Math.floor(v.z / UNITSIZE + mapW/2 - .5);
		return {x: x, z: z};
	}
	
	function checkWallCollision(v) {
		
		var c = getMapSector(v);
		return map[c.x][c.z] > 0;
	
	}
	
	function initial( container ){
		
		domElement = container || document.body;
		if( domElement ){
			WIDTH = domElement.clientWidth;
			HEIGHT = domElement.clientHeight;
		}
		
		projector = new t.Projector(); // Used in bullet projection
		scene = new t.Scene(); // Holds all objects in the canvas
		scene.fog = new t.FogExp2(0xD6F1FF, 0.0005); // color, density
		
		// Set up camera
		cam = new t.PerspectiveCamera(60, ASPECT, 1, 10000); // FOV, aspect, near, far
		/*cam.position.x = 0 ;
		cam.position.y = UNITSIZE * .2;
		cam.position.z = UNITSIZE;
		*/
		cam.position.x = -50;
		cam.position.y = 30;
		cam.position.z = 300;
		scene.add(cam);
		
		// Camera moves with mouse, flies around with WASD/arrow keys
		controls = new t.FirstPersonControls( cam , domElement);
		controls.movementSpeed = MOVESPEED;
		controls.lookSpeed = LOOKSPEED;
		controls.lookVertical = false; // Temporary solution; play on flat surfaces only
		controls.noFly = true;
		
		// World objects
		setupScene();
		
		// Handle drawing as WebGL (faster than Canvas but less supported)
		renderer = new t.WebGLRenderer();
		renderer.setSize(WIDTH, HEIGHT);
		
		// Add the canvas to the document
		renderer.domElement.style.backgroundColor = '#D6F1FF'; // easier to see
		domElement.appendChild(renderer.domElement);
		

		
		//add full window
		THREEx.FullScreen.bindKey({ charCode : 'a'.charCodeAt(0) , element : domElement});
		
		// 偏移修正量
		var parent = domElement; 
  		while(parent){
    		numx += parent.offsetLeft;
   			numy +=parent.offsetTop;
    		parent = parent.offsetParent;
 		}
  		
	}
	
	function animate(){
		requestAnimationFrame(animate);
		render();
	}
	
	function render(){
		
		renderer.render(scene, cam); // Repaint
		renderer.setClearColor(0xffffff);
		
	}

	function setupLight(){

		// Lighting
		var directionalLight1 = new t.DirectionalLight( 0xFFFFFF, 0.7 );
		directionalLight1.position.set( 0.5, 1, 0.5 );
		scene.add( directionalLight1 );
		var directionalLight2 = new t.DirectionalLight( 0xFFFFFF, 0.5 );
		//var directionalLight2 = new t.DirectionalLight( 0xF7EFBE, 0.5 );
		directionalLight2.position.set( -0.5, -1, -0.5 );
		scene.add( directionalLight2 );

	}
    
    /**
    * prepare hiden model for use
    * prepared 
    *      - palletrack
    *      - greenbox
    */
	function setupmodelPros(){
        
        // palletrack
        new THREE.ColladaLoader().load( '../public/threeD/models/palletrack.dae' , function ( result ){
	  			
	  			var track = result.scene; 
	  			track.rotateX( - 0.5 * Math.PI );
	  			track.rotateZ(  Math.PI );	
	  			track.scale.x =  track.scale.y  = track.scale.z = 0.2;
	  			track.name = 'palletrack';

	            modelsPros.palletrack = track;
	  	});

	  	//green box
	  	new THREE.ColladaLoader().load( '../public/threeD/models/Green.dae' , function ( result ){
	  			
	  			var box = result.scene; 
	  			box.scale.x = 0.2;
	  			box.scale.y = 0.1;
	  			box.scale.z = 0.3;
	  			box.name = 'box';

	  			modelsPros.greenbox = box;
	  	 });

	  	//temp sensor
	  	new THREE.ColladaLoader().load( '../public/threeD/models/scan.dae' , function ( result ){
	  			
	  			var sensor = result.scene; 
	  			sensor.scale.x =  sensor.scale.y  = sensor.scale.z = .8;
	  			sensor.name = 'sensor';

	  			modelsPros.sensor = sensor;
	  	 });

	}
	
	function  setupScene()
	{	 
        setupLight();
        setupmodelPros();
		drawWarahouse();
	}
	
	 // scene中按name属性查找object3D群组
    function getObjectsByName( name )
    {
    	var results = [];
    	for (var i = scene.children.length -1 ; i >= 0 ; i--){
    	    if( scene.children[i].name == name ){
    	    	results.push(scene.children[i]);
    	    }
    	}
    	return results;
    }
    
    //count model bounding box
    function getComplexBoundingBox(object3D) {
        var box = null;
        object3D.traverse(function(obj3D) {
            if (obj3D.matrixWorldNeedsUpdate) obj3D.updateMatrixWorld();
            var geometry = obj3D.geometry;
            // If current is not a geometry (THREE.Geometry), proceed to the next one
            if (geometry === undefined) return null;
            // If this object is already bounding box, then use it
            if (geometry.boundingBox) { 
                var workableBox = geometry.boundingBox.clone();
                // Move the resulting bounding box to the position of the object itself
                workableBox.applyMatrix4(obj3D.matrixWorld);
                if (box === null) {
                    box = workableBox;
                } else {
                    box.union(workableBox);
                }
            // If there is no bounding box for current object - creating
            } else {
                var workableGeometry = geometry.clone();
                // Move the resulting geometry in the position of the object itself
                workableGeometry.applyMatrix(obj3D.matrixWorld);
                // Calculate the bounding box for the resulting geometry
                workableGeometry.computeBoundingBox();
                if (box === null) {
                    box = workableGeometry.boundingBox;
                } else {
                    box.union(workableGeometry.boundingBox);
                }
            }
        });
        return box;
    } 
    
    var handleClick = function( evt ){
    	
       clickInfo.userHasClicked = true;
  	   clickInfo.x = evt.clientX;
  	   clickInfo.y = evt.clientY;
  	   
  	   if( clickInfo.userHasClicked ){
  		 clickInfo.userHasClicked = false;
  		 
  		 var top = document.documentElement.scrollTop + document.body.scrollTop;
  		 var x = ( (clickInfo.x - numx)/ WIDTH ) * 2 - 1;
		 var y = -( (clickInfo.y - (numy - top))/ HEIGHT ) * 2 + 1;
		 
		 directionVector.set(x, y, 1);
		 projector.unprojectVector(directionVector, cam);
 		 directionVector.sub(cam.position);
 		 directionVector.normalize();
 		 raycaster.set(cam.position, directionVector);
 		 
 		 var intersects = raycaster.intersectObjects(scene.children, true);
 		 if (intersects.length) {
 			var target = intersects[0].object;
 			
 			//直接寻找父亲 直至所有物体的parent都是scene;
 			while( target.parent && !(target.parent instanceof THREE.Scene) )
 		    {
 		       target = target.parent;
 		    }
 			
 			if( target.name == 'warahouse' );
 			else{
 				
 				//第一次选中后，原有的box外部套上boundingbox 点击后先选中boundingbox
 	 			if( target instanceof THREE.BoundingBoxHelper ){
 	 				//为二次点击
 	 				bbHelper = target ;

 	 				if( target.name == 'palletrack')
 	 				{
 	 					//for the box is in the track , we should remove the track's boxhelper 
 	 				    //and change the target into the next selection(box/track)
 	 				    scene.remove( bbHelper );
                        target = intersects[1].object;
 	 				}
 	 				else{
 	 					bbHelper.visible = !(bbHelper.visible) ;
 	 				}
 	 			} 
 	 			if( target instanceof THREE.BoundingBoxHelper ) ;
 	 			else if( target instanceof THREE.Object3D ){
 	 				
 	 				if( !bbHelper ){
 	 					bbHelper = new THREE.BoundingBoxHelper( target , 0xffffff );
 	 					scene.add( bbHelper );
 	 				}
 	 				else{
 	 					scene.remove( bbHelper );
 	 					bbHelper = new THREE.BoundingBoxHelper( target , 0xffffff );
 	 					scene.add( bbHelper );
 	 				}
 	 				
 	 				bbHelper.name = target.name;
 	 				bbHelper.id = target.id;
 	 				bbHelper.uerData = target.userData;
 	 						
 	 			}
 	 			
 	 			bbHelper.update();		
 	 			
 			}
 			
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
	
	
	//datas
	
	//boxes
	 var getBoxInfo = function () {
         
	  		var proInfo ;
	  		// ajax请求后台当前仓库信息.
	  		jQuery.ajax({ 
				type:'POST',
				url:'inventory?action=query',
				success:function(data){
					proInfo = JSON.parse(data).proList;
				},
	            async:false
			}); 
	  		
	       return  proInfo;
	  	};
	  	
	 var getSensorInfo = function () {
		
		 var sensorInfo ;
		 jQuery.ajax({
			 type:'POST',
			 url:'../TempMonitor/querySensor',
			 success:function(data){
				 sensorInfo = JSON.parse(data).sensorList;
			 },
			 async:false
		 });
		 
		 return  sensorInfo;
	 };
	 
	 var drawWarahouse = function(){
		 
		 new THREE.ColladaLoader().load( '../public/threeD/models/warehouse.dae' , function ( result ){
	  			
	  			var house = result.scene; 	

	  			house.rotateX( - 0.5 * Math.PI );
	  			house.rotateZ(  Math.PI );
	  			
	  			house.scale.x =  house.scale.y  = house.scale.z = 0.8;
	  			house.name = 'warahouse';
	  			
	  			scene.add(house);

	  		});
	 }
     
   var drawBox = function (){
		    
		//get the inventory info
		var proList =  getBoxInfo();

		for( var i = proList.length - 1 , x_index = 0 , y_index = 0 , z_index = 0 , tarck_index = 0; i >= 0 ; i--)
		{
			var pro = proList[i];
			var boxCount = Math.ceil(pro.count / 80);
			if( !boxCount ) continue;

			//prepare the green box in advance  ,named modelsPros.greenbox
			//prepare the palletrack in advance ,named modelsPros.palletrack

            //draw the palletrack
			//count the palletrack we need ; a track can held 6*2*2 green box;
			var trackNum = Math.ceil( boxCount / 24 );
			// array set to record track positions
			//TODO change to store the x only
			var trackPosArray = [];

			for( var j = trackNum ;  j > 0 ; j -= 2 , tarck_index++)
			{
				//draw left
				var trackLeft = modelsPros.palletrack.clone();
				trackLeft.position.x = -100;
	            trackLeft.position.z = 10 + 20*tarck_index;
	            scene.add(trackLeft);
	            trackPosArray.push(trackLeft.position);

	            //the track info
	            trackLeft.userData.trackInfo ={
	            	name : pro.name ,
	            	index : j ,
	            	trackSum : trackNum ,
	            	boxCount : j == 1 ? boxCount - (trackNum - 1) * 24 : 24 
	            } ;

		        if( j >= 2)
		        {
		        	//draw right
		        	var trackRight = modelsPros.palletrack.clone();
		        	trackRight.position.x = -30;
		        	trackRight.position.z = trackLeft.position.z ;
		        	scene.add(trackRight);
		        	trackPosArray.push(trackRight.position);
		        	trackLeft.userData.trackInfo ={
	            	    name : pro.name ,
	            	    index : j ,
	            	    tarckSum : trackNum ,
	            	    boxCount : j == 2 ? boxCount - (trackNum - 1) * 24 : 24 
	                } ;
		        }
		    }

            //draw the boxes
            for( var j = 0 , x_i = 0 , y_i = 0 , slot_i = 0;  j < boxCount ; j++ )
            {
                var parentPos = trackPosArray[ parseInt(j/24) ];
                var greenbox = modelsPros.greenbox.clone();

                //set box position here
                //TODO can have a better way to caculate the positon
                greenbox.position.x =  parentPos.x - 20 + 11 * x_i;
                greenbox.position.y =  5 + y_i * 5 + slot_i * 11;
                greenbox.position.z =  parentPos.z - 4;
                scene.add(greenbox);
                greenbox.userData.proInfo = pro;

                //count the x_index, y_index, z_index
                //x_max = 4 , z_max = 1 , y_max = 2 for one slot ,3 slot in height
                if( x_i  >= 3 )
                {
                	//change into next y
                	if( y_i >= 1 )
                	{
                		slot_i++ ;
                		y_i = 0;
                		x_i = 0;
                	}
                	else
                	{
                		y_i++ ;
                		x_i = 0;
                	}
                }
                else
                {
                	x_i++;
                }

            }


		}
	}
	  	 
	var toggle = function ( flag , name , callback){
	 
	 var objs = getObjectsByName( name );
	 if( objs.length == 0  && flag) callback();
	 else{
		 for ( var i = objs.length -1 ; i>=0 ;i--){
			    objs[i].traverse( function ( object ) { object.visible = flag; } );
				}
	 }
	};
		
	var toggleBox = function ( flag ){		 
	 toggle( flag , 'box' , drawBox );		 
	 } ;
	 
	 var toggleReader = function ( flag ){
		 toggle( flag , 'reader' , drawReader );
	 } ;
	 
	 var toggleSensor = function ( flag ){
		toggle( flag , 'sensor' , drawSensor );
	 } ;

	var drawSensor = function(){

		var sensorList = getSensorInfo();
		
	 	//sensor positon standard should be
	 	//left hand first - ... :(-3,40,10),(-3,40,10+40)...
        //right hand : (-123,40,20),(-123,40,20+40)...

	 	for( var i = sensorList.length - 1 ; i >= 0 ; i--)
	 	{
	 		var sensor = modelsPros.sensor.clone();
	 		sensor.userData.sensorInfo = sensorList[i];

	 		var properties = sensorList[i].position.split(','); 
	 		var position = {};
		    properties.forEach( function (pro)
		    {
		    	var tup = pro.split(':');
		    	position[tup[0]] = tup[1];
		    });
		    
		    sensor.rotateX( -0.5 * Math.PI );
		    if( position.x == -3 ) 
		    {
		    	//left hand
		    	sensor.rotateZ( -0.5 * Math.PI );

		    }
		    else if ( position.x == -123)
		    {
		    	//right hand
		    	sensor.rotateZ( 0.5 * Math.PI );

		    }
		    
		    sensor.position.set( position.x , position.y , position.z );
		    scene.add(sensor); 

	 	}
	}

	 var drawReader = function (){
		 

		new THREE.ColladaLoader().load( '../public/threeD/models/gate.dae' , function ( result ){
				
				var dae = result.scene;
				dae.position.z = 250  ;
				dae.position.x = -50 ;
				dae.position.y = 0 ;
		
				dae.rotateX( -0.5 * Math.PI );
				dae.rotateZ( 0.5 * Math.PI );
				dae.scale.x =  dae.scale.y  = dae.scale.z = 0.4;
				dae.name = 'reader';
				scene.add( dae );
			});
	 }
	//datas
	
	return {
	    init:function( domElement ){
	    	initial( domElement );
	    	animate();
	    },
	    
	    checkWall : checkWallCollision ,
	    
	    toggleBox : toggleBox ,
	    
	    toggleReader : toggleReader ,
	    
	    toggleSensor : toggleSensor ,
	    
	    getMouseClickInfo : handleClick
	    
	      	
	};
};