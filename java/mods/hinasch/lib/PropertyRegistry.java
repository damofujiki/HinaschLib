package mods.hinasch.lib;

import java.util.Collection;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.primitive.IPropertyElement;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public abstract class PropertyRegistry<T extends IPropertyElement> extends RegistrySimple<ResourceLocation,T> {


	public abstract void init();
	public abstract void preInit();
	protected T put(T p){
		this.register(p.getKey(), p);
		return p;
	}
	protected void put(T... p){
		for(T t:p){
			this.register(t.getKey(), t);
		}


	}

    public void register(ResourceLocation key, T value)
    {
        super.putObject(key, value);
        HSLib.logger.trace("registering...", value.getName()+" to "+this.toString());
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
