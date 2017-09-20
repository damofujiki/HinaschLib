package mods.hinasch.lib.core;

import mods.hinasch.lib.client.ClientHelper.ModelHelper;
import mods.hinasch.lib.client.ClientHelper.PluralVariantsModelFactory;
import mods.hinasch.lib.client.RenderPlayerHook;
import mods.hinasch.lib.debuff.ItemIconBuff;
import mods.hinasch.lib.util.HSLibs;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {

		final ModelHelper modelAgent = new ModelHelper(HSLib.MODID);

//		PluralVariantsModelFactory.create(modelAgent, HSLib.items.itemParticleIcon)
//		.prepareVariants(ParticleTypeWrapper.Particles.getJsonNames())
//		.attach(ParticleTypeWrapper.Particles.getJsonNames().iterator(), (String input,Integer index) -> (modelAgent.getNewModelResource(input, "inventory")));

		PluralVariantsModelFactory.create(modelAgent, HSLib.core().items.itemIconBuff)
		.prepareVariants(ItemIconBuff.getJsonNames())
		.attach(ItemIconBuff.getJsonNames().iterator(), (String input,Integer index) -> (modelAgent.getNewModelResource(input, "inventory")));
//		ClientHelper.addToModelBakeryWithVariantName(itemIconBuff, MODID, ItemIconBuff.getJsonNames());
//		ClientHelper.registerWithSubTypes(itemIconBuff, ItemIconBuff.getJsonNames(), new ModelResourceFunction<String>(){
//
//			@Override
//			public ModelResourceLocation apply(String input,Integer index) {
//				return modelAgent.getNewModelResource(input, "inventory");
//			}}
//		);
	}

	@Override
	public void registerEvents(){
		super.registerEvents();
		HSLibs.registerEvent(RenderPlayerHook.instance());
	}
}
