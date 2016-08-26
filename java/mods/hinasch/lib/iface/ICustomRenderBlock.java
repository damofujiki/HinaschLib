package mods.hinasch.lib.iface;


/**
 * 使う必要とくにない、リマインダー
 *
 *
 */
public interface ICustomRenderBlock {

	public boolean isOpaqueCube();


	public boolean renderAsNormalBlock();


    public int getRenderType();
    

    public final boolean IS_NOT_OPAQUE = false;
    public final boolean DONT_RENDER_AS_NORMAL = false;
}
