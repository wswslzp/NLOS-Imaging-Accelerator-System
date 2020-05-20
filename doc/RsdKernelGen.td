Header (
	company("Forte Design Systems") 
	product("TimingDesigner") 
	rev("8.002") 
	file("d:\\lzp\\nsol\\nlos_system\\doc\\rsdkernelgen.td") 
	date("05/18/2020") time("11:15") 
	format("8.0") 
	baseScale(1000.000000) 
	zoomFactor("0.00105036") 
	zoomBegin(0) 
	zoomEnd(332047) 
	simHDL(Verilog) 
	simDelayMechanism(Transport) 
	nextID(14) 
	TimingAnalysisMethod(51) 
	user(date,"") user(description,"") user(designer,"") user(revision,"1.0") 
	generalPrintParams( "printer,,,portrait,jpg,letter,default,6,5,in,color,scaleable,")
	diagramPrintParams( "0,7,0,331290,true,true,1,1,true,false,false,false,all,diagramEnd")
	spreadsheetPrintParams( "0,2147483647,true,false,1,true,all")
	)
HDLHeader( 
	hdlFile("") 
	checkConstraints(true) 
	checkTimeout(never) 
	
	command( name("") cmdType(null) ))
DefaultLabel(
	delay(showNameString,"") 
	constraint(showNameString,"") 
	guarantee(showNameString,"") 
	measure(showMeasure,"") 
	store(showNameString,"") 
	wait(showNameString,"") 
	end(showNameString,"") 
	boundary(showIncrement,"") 
	loop(showNameString,"") 
	waveform(showSkip,"","") 
	specifiedclock(showSkip,"","") 
	derivedclock(showSkip,"","") 
	simwaveform(showSkip,"","") 
	bus(showValue,"","H") 
	)
DiagramStyle (
	waveformFont(winFont("Arial",90) ) 
	rulerFont(winFont("Arial",90) ) 
	relationshipFont(winFont("Arial",90) ) 
	labelFont(winFont("Arial",90) ) 
	annotationFont(winFont("Arial",80) ) 
	slotHeight("11") 
	edgeHeight("11") 
	edgeGrade("1.96261") 
	inputPenStyle(0) 
	outputPenStyle(0) 
	inoutPenStyle(0) 
	internalPenStyle(0) 
	vRelationshipPenStyle(0) 
	hRelationshipPenStyle(0) 
	inputPenColor(3) 
	outputPenColor(3) 
	inoutPenColor(3) 
	internalPenColor(3) 
	wavePenWidth("0") 
	relationshipPenWidth("0") 
	wavesOnTop 
	relationshipsOnTop 
	annotationsOnTop 
	showSlopedEdges 
	edgeDisplayType(Uncertainty) 
	)
Clock ( objectID(2) name("clk1") minheight("0") periodScale(1000.000) period(5.000000) dutyCycle(50.000000) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Waveform ( objectID(4) name("reset") minheight("0") edges(
	(high,0.000,0.000,L), 
	(low,6000.000,6000.000), 
	(high,21000.000,21000.000), 
	(low,325000.000,325000.000), 
	(blank,331117.000,331117.000)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Waveform ( objectID(8) name("coef_valid") minheight("0") edges(
	(low,0.000,0.000,L), 
	(high,49000.000,49000.000), 
	(low,55000.000,55000.000), 
	(blank,154015.000,154015.000)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Bus ( objectID(9) name("coef") minheight("0") edges(
	(z,0.000,0.000,L), 
	(valid,49000.000,49000.000), 
	(z,55000.000,55000.000), 
	(blank,155015.000,155015.000)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Waveform ( objectID(10) name("ring_impulse_valid") minheight("0") edges(
	(low,0.000,0.000,L), 
	(high,75000.000,75000.000), 
	(low,140000.000,140000.000), 
	(blank,232000.000,232000.000)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Bus ( objectID(11) name("ring_impulse") minheight("0") edges(
	(z,0.000,0.000,L), 
	(valid,74000.000,74000.000), 
	(valid,80000.000,80000.000), 
	(valid,85000.000,85000.000), 
	(valid,90000.000,90000.000), 
	(valid,95000.000,95000.000), 
	(valid,100000.000,100000.000), 
	(valid,105000.000,105000.000), 
	(valid,110000.000,110000.000), 
	(valid,115000.000,115000.000), 
	(valid,119000.000,119000.000), 
	(valid,125000.000,125000.000), 
	(valid,130000.000,130000.000), 
	(valid,135000.000,135000.000), 
	(z,139000.000,139000.000), 
	(blank,232000.000,232000.000)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Waveform ( objectID(12) name("kernel_valid") minheight("0") edges(
	(low,0.000,0.000,L), 
	(high,120000.000,120000.000), 
	(low,189000.000,189000.000), 
	(blank,254015.000,254015.000)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
Bus ( objectID(13) name("kernel") minheight("0") edges(
	(z,0.000,0.000,L), 
	(valid,119000.000,119000.000), 
	(z,190000.000,190000.000), 
	(blank,257015.463,257015.463)) 
	hdl( direction(internal) ) label(showDefault,"","H") )
EndCycleEvent ( objectID(1) name("end") time(331.291347) showVert hideOnPrinter label(showDefault,"") fromLatestEnd toEarliestBegin  )
