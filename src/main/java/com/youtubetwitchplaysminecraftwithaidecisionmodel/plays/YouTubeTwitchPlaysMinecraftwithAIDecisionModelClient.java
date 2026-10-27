package com.youtubetwitchplaysminecraftwithaidecisionmodel.plays;

// TODO are you going to use
// TODO net.minecraft.network.chat.TextColor ?
// TODO public static final TextColor GOLD = TextColor.fromRgb(0xD4AF37);

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import net.minecraft.util.GsonHelper;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import net.minecraft.network.chat.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, value = Dist.CLIENT)
public class YouTubeTwitchPlaysMinecraftwithAIDecisionModelClient {
    public YouTubeTwitchPlaysMinecraftwithAIDecisionModelClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("HELLO FROM CLIENT SETUP");
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    // TODO JSON Parse
    private static String timetoken = "1000";
    private static boolean subscribed = false;

    static void sendAsyncGetRequest() {
        //GET /subscribe/:sub_key/:channel/:callback/:timetoken
        //GET /subscribe/:sub_key/:channel/:callback/:timetoken
        //GET /subscribe/:sub_key/:channel/:callback/:timetoken
        String endpoint = "https://ps.pndsn.com";
        String channel = System.getenv("YOUTUBE_TWITCH_CHANNEL");
        String url = endpoint + "/subscribe/demo/" + channel + "/0/" + timetoken;
        //String url = endpoint + "/time/0";

        Minecraft mc1 = Minecraft.getInstance();
	HttpClient client = HttpClient.newHttpClient();
	HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create(url))
		.GET()
		.build();
        
        // [[{"user":"@QuickfixKBPAWER","message":"erik say hi","platform":"youtube_account"},{"user":"@kailevia","message":"so nice to see sm1 not use AI to code 😊","platform":"youtube_account"}],"17931151713075151"]
	client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
	      .thenAccept(resp -> {
                  String response = resp.body();
                  JsonArray rootArray = new Gson().fromJson(response, JsonArray.class);
                  JsonArray chatArray = rootArray.get(0).getAsJsonArray();
                  String tt = rootArray.get(1).getAsString();
                  //timetoken = tt;
                  // TODO JSON Parse
                  // TODO capture the Timetoken 
                  // TODO 
                  YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("Response: {}", response);
                  // Chat is not thread-safe: hop back to the client thread.
                  Minecraft mc = Minecraft.getInstance();
                  mc.execute(() -> {
                      if (mc.player == null) return;
                      for (JsonElement element : chatArray) {
                          JsonObject chatObject = element.getAsJsonObject();
                          String user = chatObject.get("user").getAsString();
                          String message = chatObject.get("message").getAsString();
                          String platform = chatObject.get("platform").getAsString();
                          mc.player.sendSystemMessage(Component.literal(user  + ": " + message));
                      }
                  });
	      })
	      .exceptionally(ex -> {
                  YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("Request FAILED", ex);
                  return null;
	      });
    }

    private static int clientTicks = 0;

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        // TODO Check if subscription is active
        Minecraft mc = Minecraft.getInstance();
        if (clientTicks % 10 == 0) {
            YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("clientTicks: {} {}", clientTicks, mc.player);
        }
        if (mc.player == null) return;
        if (clientTicks++ % 100 == 0) {
            sendAsyncGetRequest();
        }
    }

    @SubscribeEvent
    static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
            Identifier.fromNamespaceAndPath(YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, "ai_timer"),
            (guiGraphics, deltaTracker) -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.gui.hud.isHidden() || mc.player == null) return;
                String label = "HELLO Youtube and Twtich Chat!!!!!";
                // X = 10, Y = 10, Color = 0xFFFFFFFF (White), dropShadow = true
                guiGraphics.text(mc.font, label, 10, 10, 0xFFFF2222, true);
            }
        );
    }

}
