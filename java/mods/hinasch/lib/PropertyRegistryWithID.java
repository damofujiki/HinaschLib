package mods.hinasch.lib;

import java.util.Collection;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.primitive.PropertyElementWithID;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;

public abstract class PropertyRegistryWithID<T extends PropertyElementWithID> extends RegistryNamespaced<ResourceLocation,T> {



	public abstract void init();
	public abstract void preInit();
	protected T put(T p){
		this.register(p.getId(),p.getKey(), p);
		return p;
	}
	@Override
    public void register(int id, ResourceLocation key, T value)
    {
        super.register(id, key, value);
        HSLib.logger.trace("registering...", value.getName()+" to ID"+String.valueOf(id));
    }
	public T get(String name){
		return this.getObject(new ResourceLocation(name));
	}



	public int getLength(){

		return this.registryObjects.size();
	}

	protected abstract void registerObjects();
	public Collection<T> getProperties(){
		return this.registryObjects.values();
	}
}
