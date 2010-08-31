// ActionScript file
import mx.controls.Alert;
import mx.events.MenuEvent;
import mx.events.ModuleEvent;

public function carregarModulo(module:String):void{
	
	moduleLoader.addEventListener(ModuleEvent.ERROR,onError);
	moduleLoader.unloadModule();
	moduleLoader.loadModule("view/modules/" + module + ".swf");
}

public function onError(erro:ModuleEvent):void{
	Alert.show("Ocorreu um erro ao carregar o módulo.");
}

/**
 * Trata o evento do clique do menu superior.
 */   
public function handleItemClick(event:MenuEvent):void {
    carregarModulo(event.item.@name);
}
	