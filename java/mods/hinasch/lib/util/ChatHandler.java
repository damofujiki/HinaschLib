package mods.hinasch.lib.util;


import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.server.FMLServerHandler;

/**
 *
 *from https://github.com/SackCastellon/SKC-Core/blob/master/java/SackCastellon/core/handler/ChatMessageHandler.java
 *
 */

public abstract class ChatHandler {

  private static ITextComponent SkcChatComponent;


  public static void iCommandSenderReply(ICommandSender player, String message) {
      sendChatToPlayer((EntityPlayer)player, message);
  }

  private static ITextComponent createSckChatComponent(String string) {
      TextComponentString Component = new TextComponentString(string);
        return Component;
  }

  public static ITextComponent createChatComponent(String message) {
	  TextComponentString component = new TextComponentString(message);
      return component; //SkcChatComponent.appendSibling(component);
  }

  /**
   * playerがEntityPlayerにキャストできる場合だけメッセージを送る
   * @param player
   * @param message
   */
  public static void sendChatToPlayerOrThrough(EntityLivingBase player, String message){
	  if(player instanceof EntityPlayer){
		  sendChatToPlayer((EntityPlayer) player,message);
	  }
  }
  public static void sendChatToPlayer(EntityPlayer player, String message) {
	  player.addChatComponentMessage(createChatComponent(message));
  }

  public static void broadcastMessageToPlayers(String message){
	  FMLServerHandler.instance().getServer().addChatMessage(createChatComponent(message));
//      MinecraftServer.getServer().getConfigurationManager().sendChatMsg(createChatComponent(message));
  }

  public static void sendLocalizedMessageToPlayer(EntityPlayer player, String message){
      player.addChatComponentMessage(createChatComponent(HSLibs.translateKey(message)));
  }
}
