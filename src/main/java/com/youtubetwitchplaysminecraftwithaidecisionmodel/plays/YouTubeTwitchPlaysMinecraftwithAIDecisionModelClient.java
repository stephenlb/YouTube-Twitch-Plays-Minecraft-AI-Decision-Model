package com.youtubetwitchplaysminecraftwithaidecisionmodel.plays;

// TODO are you going to use
// TODO net.minecraft.network.chat.TextColor ?
// TODO public static final TextColor GOLD = TextColor.fromRgb(0xD4AF37);
// TODO add NeoForge Logo
// TODO bits - when donos Deena - do somethign in the game

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import net.minecraft.util.GsonHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.client.Options;
import net.minecraft.client.player.KeyboardInput;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Input;
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
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;

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
    // TODO op.awMelon: Birdflop is a website that shows you which colour corresponds to which symbol. I thought it might be helpful! You should also check out resources for mods, as you can use custom fonts, textures, and even custom emojis.

    // TODO JSON Parse
    private static String timetoken = "1000";
    private static boolean subscribed = false;
    private static List<String> chatMessages = new ArrayList<>();
    private static Map<String, String> playerColors = Map.of(
        "@AronDenBasta", "§2",
        "@UnKitty2", "§9",
        "@QuickfixKBPAWER", "§5",
        "@ilovemydogKBPAWER", "§5",
        "@catguyYT", "§a",
        "lecaptnsky", "§3",
        "stephenlb", "§4",
        "@StephenBlum", "§4",
        "@Neon_AI_Lab", "§1",
        "@Codemitsu888", "§7"
    );
    /// @QuickfixKBPAWER §6§lMINECRAFT COLORS: §r§00 §1DarkBlue §2Green §3Cyan §4Red §5Purple §6Gold §7Gray §8DarkGray §9Blue §aLime §bAqua §cRed §dPink §eYellow §fWhite

    static void subscribe() {
        subscribed = true;
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
                  subscribed = false;
                  String response = resp.body();
                  JsonArray rootArray = new Gson().fromJson(response, JsonArray.class);
                  JsonArray chatArray = rootArray.get(0).getAsJsonArray();
                  String tt = rootArray.get(1).getAsString();
                  timetoken = tt;

                  YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("Response: {}", response);

                  Minecraft mc = Minecraft.getInstance();
                  mc.execute(() -> {
                      if (mc.player == null) return;
                      for (JsonElement element : chatArray) {
                          JsonObject chatObject = element.getAsJsonObject();
                          String user = chatObject.get("user").getAsString();
                          String message = chatObject.get("message").getAsString();
                          String platform = chatObject.get("platform").getAsString();

                          // TODO Name change dictinoary
                          // TODO Translate to english
                          // TODO per-user color lookup hashmapp

                          // AronDenBasta = Green
                          // UnKitty = Blue
                          // LeCAPTNSky = Cyan §b
                          // Quickfix = Purple
                          //§cQ§6U§eI§aC§bK§3F§dI§5X§r is the best
                          // Maybe red names for YT texts and Purple For Twitch? ( Disruptor / ThatOneUnknownBear)

                          String color = playerColors.getOrDefault(user, "§c");
                          chatMessages.add(color + user + "§r: §6" + message);

                          mc.player.sendSystemMessage(
                              Component.literal(user).withStyle(ChatFormatting.GOLD)
                              .append(Component.literal(": " + message).withStyle(ChatFormatting.WHITE))
                        );
                      }
                  });
	      })
	      .exceptionally(ex -> {
                  subscribed = false;
                  YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("Request FAILED", ex);
                  return null;
	      });
    }

    private static int clientTicks = 0;

    /** Keyboard input that is always pushing forward (other keys still work). */
    private static class ForcedForwardInput extends KeyboardInput {
        ForcedForwardInput(Options options) {
            super(options);
        }

        @Override
        public void tick() {
            super.tick();
            Input k = this.keyPresses;
            this.keyPresses = new Input(true, k.backward(), k.left(), k.right(), k.jump(), k.shift(), k.sprint());
            // Recompute the move vector the physics actually reads (forward cancels backward).
            float left = k.left() == k.right() ? 0f : (k.left() ? 1f : -1f);
            float forward = k.backward() ? 0f : 1f;
            this.moveVector = new Vec2(left, forward).normalized();
        }
    }

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        // TODO Check if subscription is active
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        //LocalPlayer player = Minecraft.getInstance().player;
        /*
        if (player != null) {
            //player.input.forwardImpulsemovementForward = 1.0f; // Force move forward
            player.input.keyPresses = new Input(true, false, false, false, false, false, false);
        }
        */
        //if (false && player != null && mc.screen == null) {
        if (player != null && !(player.input instanceof ForcedForwardInput)) {
            // Swap in an input source that always holds "forward". LocalPlayer ticks this itself,
            // so movement goes through normal physics (collisions, sprint, step-up, etc).
            player.input = new ForcedForwardInput(mc.options);
        }

        /*
        if (clientTicks % 10 == 0) {
            YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("clientTicks: {} {}", clientTicks, mc.player);
        }
        */
        if (mc.player == null) return;
        if (subscribed) return;
        //if (clientTicks++ % 100 == 0) {
        subscribe();
        //}
    }

    @SubscribeEvent
    static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
            Identifier.fromNamespaceAndPath(YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, "ai_timer"),
            (guiGraphics, deltaTracker) -> {
                // TODO  static the mc instance
                Minecraft mc = Minecraft.getInstance();
                if (mc.gui.hud.isHidden() || mc.player == null) return;
                String label = "HELLO Youtube\n\r and \r\nTwitch Chat!!!!!";
                int rightMargin = guiGraphics.guiWidth(); 
                //int rightMargin = 200;
                int textWidth = mc.font.width(label);
                int xPosition = rightMargin - textWidth - 100;
                guiGraphics.text(mc.font, label, xPosition, 10, 0xFFFF2222, true);

                int xChatPos = 0;
                int yChatPos = 0;
                // TODO only prune the list when .add occurs
                List<String> chats = chatMessages.reversed();
                // Color chat messages
                // Alerts and notifications 
                // Player Controls
                // @tag lightblue bold
                //§cQ§6U§eI§aC§bK§3F§dI§5X§r is the best
                for (String msg : chats.subList(0, Math.min(10, chats.size())).reversed()) {
                    guiGraphics.text(mc.font, msg, xChatPos, yChatPos, 0xFFFFFFFF, true);
                    yChatPos += 11;
                }
            }
        );
    }

    /*
    @SubscribeEvent
    static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        // TODO Check if subscription is active
        
        // Instead of fetching from Minecraft.getInstance(), use the event's built-in player context
        LocalPlayer player = event.getEntity(); 
        
        if (player != null) {
            // This completely overrides the keyboard state right before physics calculation
            player.input.keyPresses = new Input(true, false, false, false, false, false, false);
        }
    }
    */


    /*
    @SubscribeEvent
    static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        // TODO Check if subscription is active
        
        // Check if the entity updating their movement is the local client player
        if (event.getEntity() instanceof LocalPlayer player) {
            // Apply your forced forward movement
            player.input.keyPresses = new Input(true, false, false, false, false, false, false);
        }
    }*/
}
