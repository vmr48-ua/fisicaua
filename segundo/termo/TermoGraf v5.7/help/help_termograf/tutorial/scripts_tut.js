/*
 FUNCIONES PARA INSERTAR UN MENU DE OBJETOS FLASH/SHOCKWAVE
 array[0]: width; array[1]: height; array[2]: nombre; array[3]: path; array[4]: auxiliar
*/

var tg_arrayPrev = new Image();
var tg_arrayNext = new Image();

// devuelve el numero de flash actual segun el query de inLocation
function tg_arrayGetCurrent(inLocation) {
  var auxCurrent = new Number(inLocation.search.substring(1));
  if (auxCurrent.valueOf()==Number.NaN || auxCurrent==null 
      || auxCurrent=="" || auxCurrent=="0") { 
	auxCurrent=0; 
  }
  return auxCurrent;
}

// devuelve el indice relativo al actual en el array de nombres
function tg_arrayGetIndex_relativo(inArray,inCurrent,inInc) {
  if (inCurrent==null || inCurrent=="" || inCurrent=="0") { return inCurrent; }
  var auxWho = inCurrent+inInc;
  if (auxWho==-1) { return inArray.length-1; }
  if (auxWho==inArray.length) { return 0; }
  return auxWho;  
}
// escribe el nombre del flash en el documento (inNum=0 primer flash)
function tg_arrayWriteName(inFlashArray,inNum) {
  document.write(inFlashArray[inNum][2]);
};
// escribe el dato auxiliar del flash en el documento (inNum=0 primer flash)
function tg_arrayWriteAuxData(inFlashArray,inNum) {
  document.write(inFlashArray[inNum][4]);
};
// escribe los contenidos del select en el documento
function tg_arrayWriteSelectContents(inFlashArray,inCurrent) {
  for (var i=0; i<inFlashArray.length; i++) {
    if (i==inCurrent) { document.write("<option selected=\"selected\">"+inFlashArray[i][2]+"</option>"); }
    else { document.write("<option>"+inFlashArray[i][2]+"</option>"); }
  }
};

// redirecciona al flash indicado en inSelect
function tg_arrayGoSelect(inFlashArray,inSelect) {
  var auxIndex = inSelect.selectedIndex;
  // evitamos cambio cuando NO existe el path
  if (inFlashArray[auxIndex][3]!=null) {
    tg_arrayGoNum(inFlashArray,auxIndex);
  }
}
// redirecciona a otro flash (inNum=0 primer flash)
function tg_arrayGoNum(inFlashArray,inNum) {
  var auxStr = new String(document.location);
  var auxWhere = new Number(auxStr.indexOf('?'));
  if (auxWhere>0) { auxStr = auxStr.substring(0,auxWhere); }
  document.location = auxStr+"?"+inNum;
};
// escribe el objeto Flash en el documento 
function tg_flashWrite(inFlashArray,inNum) {
  document.write("<div align=\"center\">");
  document.write("<OBJECT CLASSID=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"");
  document.write("  TITLE=\""+inFlashArray[inNum][2]+"\"");
  document.write("  WIDTH=\""+inFlashArray[inNum][0]+"\" HEIGHT=\""+inFlashArray[inNum][1]+"\"");
  document.write("  CODEBASE=\"http://active.macromedia.com/flash5/cabs/swflash.cab#version=5,0,0,0\">");
  document.write("<PARAM NAME=movie VALUE=\""+inFlashArray[inNum][3]+"\">");
  document.write("<PARAM NAME=play VALUE=true>");
  document.write("<PARAM NAME=loop VALUE=false>");
  document.write("<PARAM NAME=quality VALUE=high>");
  document.write("<EMBED SRC=\""+inFlashArray[inNum][3]+"\" ");
  document.write("  TITLE=\""+inFlashArray[inNum][2]+"\"");
  document.write("  WIDTH=\""+inFlashArray[inNum][0]+"\" HEIGHT=\""+inFlashArray[inNum][1]+"\"");
  document.write("  quality=high loop=false TYPE=\"application/x-shockwave-flash\"");
  document.write("  PLUGINSPAGE=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\">");
  document.write("</EMBED>");
  document.write("</OBJECT>");
  document.write("</div>");
  // precarga de los documentos anterior y siguiente
  // cargamos primero el siguiente, que tiene mas probabilidades
  tg_arrayNext.src = inFlashArray[tg_arrayGetIndex_relativo(inFlashArray,inNum, 1)][3];
  tg_arrayPrev.src = inFlashArray[tg_arrayGetIndex_relativo(inFlashArray,inNum,-1)][3];
}
// escribe el objeto ShockWave en el documento 
function tg_shockwaveWrite(inFlashArray,inNum) {
  document.write("<div align=\"center\">");
  document.write("<object classid=\"clsid:166B1BCA-3F9C-11CF-8075-444553540000\" ");
  document.write("   codebase=\"http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0\" ");
  document.write("   width=\""+inFlashArray[inNum][0]+"\" height=\""+inFlashArray[inNum][1]+"\">");
  document.write("<param name=src value=\""+inFlashArray[inNum][3]+"\">");
  document.write("<param name=sw1 value=\""+inFlashArray[inNum][3]+"\">");
  document.write("<param name=swStretchStyle value=fill>");
  document.write("<param name=swRemote value=\"swSaveEnabled='true' swVolume='true' swRestart='true' ");
  document.write(    " swPausePlay='true' swFastForward='true' swContextMenu='true'\">");
  document.write("<param name=bgColor value=#FFFFFF>");
  document.write("<embed src=\""+inFlashArray[inNum][3]+"\" sw1=\""+inFlashArray[inNum][3]+"\"");
  document.write(    " width=\""+inFlashArray[inNum][0]+"\" height=\""+inFlashArray[inNum][1]+"\"");
  document.write(    " bgcolor=#FFFFFF  swremote=\"swSaveEnabled='true' swVolume='true' ");
  document.write(    " swRestart='true' swPausePlay='true' swFastForward='true' swContextMenu='true'\" swstretchstyle=fill");
  document.write(    " type=\"application/x-director\"");
  document.write(    " pluginspage=\"http://www.macromedia.com/shockwave/download/\"> </embed>");
  document.write("</object>");
  document.write("</div>");
}

