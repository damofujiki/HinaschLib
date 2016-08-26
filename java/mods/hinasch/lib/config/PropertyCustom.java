package mods.hinasch.lib.config;

import com.google.common.base.Preconditions;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

//2013.11.13
public class PropertyCustom {


	public Property[] props;
	public String[] propNames;
	public String[] propCategories;
	public Object[] propValues;
	public String[] propComments;

	public static PropertyCustom newProperties(String... propNames){
		return new PropertyCustom(propNames);

	}
	public PropertyCustom(String[] par1){
		this.propNames = par1;
		this.props = new Property[this.propNames.length];
		this.propComments = new String[this.propNames.length];
		for(int i=0;i<this.propNames.length;i++){
			this.propComments[i] = "no comment.";
		}
	}

	public void setCategories(String[] par1){
		this.propCategories = par1;

	}

	public void setCategoriesAll(String par1){
		int len = this.propNames.length;
		this.propCategories = new String[len];
		for(int i=0;i<len;i++){
			this.propCategories[i] = par1;
		}
	}

	public void setComments(String... strs){
		this.propComments = strs;
	}
	public <T> void setValues(T[] par1){
		this.propValues = par1;
	}

	public <T> void setValuesSequentially(T... par1){
		int index = 0;
		this.propValues = new Object[par1.length];
		for(T obj:par1){
			this.propValues[index] = obj;
			index +=1;
		}

	}
	public void adapt(Configuration config){
		for(int i=0;i<props.length;i++){
			Preconditions.checkNotNull(this.propValues[i], "propValue is null.ぬるぽ");
			if(this.propValues[i] instanceof Boolean){
				this.props[i] = config.get(this.propCategories[i], this.propNames[i], (Boolean)this.propValues[i],this.propComments[i]);
			}
			if(this.propValues[i] instanceof Integer){
				this.props[i] = config.get(this.propCategories[i], this.propNames[i], (Integer)this.propValues[i],this.propComments[i]);
			}
			if(this.propValues[i] instanceof String){
				this.props[i] = config.get(this.propCategories[i], this.propNames[i], (String)this.propValues[i],this.propComments[i]);
			}
		}
	}

	public Property getProp(int i){
		Preconditions.checkNotNull(this.props[i],"build Props before getProp.");
		return this.props[i];
	}

//	@Deprecated
//	public static void makeProps(Configuration config,Property[] props,String[] names,String[] categories,Object[] values){
//		for(int i=0;i<props.length;i++){
//			if(values[i] instanceof Boolean){
//				props[i] = config.get(categories[i], names[i],(Boolean)values[i] );
//			}
//			if(values[i] instanceof Integer){
//				props[i] = config.get(categories[i], names[i],(Integer)values[i] );
//			}
//			if(values[i] instanceof String){
//				props[i] = config.get(categories[i], names[i],(String)values[i] );
//			}
//		}
//
//	}
}
