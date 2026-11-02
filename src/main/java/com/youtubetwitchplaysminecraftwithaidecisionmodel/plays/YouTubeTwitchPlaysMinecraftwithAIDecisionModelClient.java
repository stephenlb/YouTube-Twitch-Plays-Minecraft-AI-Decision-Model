package com.youtubetwitchplaysminecraftwithaidecisionmodel.plays;

// TODO Movement Votinig - when ppl trigger game command to move, 
// TODO hello youtube and twtich DVD Logo
// TODO add parser to set number of BLOCKS player moves
// TODO ✅ only prune the list when .add occurs
// TODO for loop on playerCommands and execyte all commands at same time
// TODO decremetn playerCommands ticks and then remove at 0 tick remaining
// TODO - idea from quickfix - website where you can control the game

// TODO op.awMelon: Birdflop is a website that shows you which colour corresponds to which symbol. I thought it might be helpful! You should also check out resources for mods, as you can use custom fonts, textures, and even custom emojis.
// TODO Name change dictinoary
// TODO Translate to english
// TODO ✅ per-user color lookup hashmapp

// TODO public static final TextColor GOLD = TextColor.fromRgb(0xD4AF37);
// TODO add NeoForge Logo
// TODO bits - when donos Deena - do somethign in the game

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

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
import net.minecraft.client.KeyMapping;
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

    // Array<Map<String, int>> 
    //private static Array<Map<String, int>> playerCommands new ArrayList<>();
    //private static Map<String, int> playerCommands new LinkedHashMap<>();


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

                          // Check for player commands
                          youtubeTwitchCommands(message);

                          // AronDenBasta = Green
                          // UnKitty = Blue
                          // LeCAPTNSky = Cyan §b
                          // Quickfix = Purple
                          //§cQ§6U§eI§aC§bK§3F§dI§5X§r is the best
                          // Maybe red names for YT texts and Purple For Twitch? ( Disruptor / ThatOneUnknownBear)

                          String color = playerColors.getOrDefault(user, "§c");
                          chatMessages.add(color + user + "§r: §6" + message);

                          // Only keep last 10 messages
                          if (chatMessages.size() >= 10) {
                              chatMessages.remove(0);
                          }

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

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        //LocalPlayer player = mc.player;
        if (!subscribed) subscribe();
        if (mc == null) return;

        for (Map.Entry<String, Integer> command : playerCommands.entrySet()) {
            String action = command.getKey();
            int frames = command.getValue() - 1;
            boolean direction = true;

            //YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("Command:" + action + "Frames:" + Integer.toString(frames));

            if (frames <= 0) {
                direction = false;
            }
            else {
                command.setValue(frames);
            } 
            /*
            ✅ keyUp / backKey / keyLeft / keyRight — Movement controls (W, S, A, D)
            ✅ keyJump — Jump
            ✅ keyShift — Sneak (Crouch)
            💜
            TODO Hotbar 
            keyAttack — Attack / Break block (Left-click)
            keySprint — Sprint
            keyInventory — Open Inventory
            keySwapOffhand — Swap item to offhand (F)
            keyDrop — Drop item (Q)
            keyUse — Use item / Place block (Right-click)
            keyPickItem — Pick block
            keyChat — Open chat
            keyCommand — Open command prompt
            keySocialInteractions — Open social interactions screen (P)
            keyPlayerList — Player list (Tab)
            keyScreenshot — Take screenshot (F2)
            keyTogglePerspective — F5 camera view toggle
            keySmoothCamera — Cinematic camera toggle
            keyFullscreen — Toggle fullscreen (F11)
            keySpectatorOutlines — Highlight players in spectator
            */
            // TODO mc.options.keyInventory.setDown(true);
            // TODO KeyMapping.click(mc.options.keyInventory.getKey()); // Increments the internal click counter
            // TODO
            KeyMapping key = switch (action) {
                case "perspective" -> mc.options.keyTogglePerspective;
                case "smooth"      -> mc.options.keySmoothCamera;
                case "inventory"   -> mc.options.keyInventory;
                case "attack"      -> mc.options.keyAttack;
                case "sprint"      -> mc.options.keySprint;
                case "jump"        -> mc.options.keyJump;
                case "crouch"      -> mc.options.keyShift;
                case "left"        -> mc.options.keyLeft;
                case "right"       -> mc.options.keyRight;
                case "forward"     -> mc.options.keyUp;
                case "backward"    -> mc.options.keyDown;
                default            -> null;
            };

            if (key != null) {
                key.setDown(direction);
                /* TODO for click commands
                for (String tgl: TOGGLES) {
                    if (msg.contains(tgl)) {
                    if (action in TOGGLES)
                    KeyMapping.click(mc.options.keyInventory.getKey()); // Increments the internal click counter
                }
                */
            }
        }
    }

    private static List<String> COMMANDS = List.of(
        "sprint", "jump", "crouch", "right", "left",
        "forward", "backward", "attack",
        "inventory", "perspective", "smooth"
    );
    private static List<String> TOGGLES = List.of(
        "inventory", "perspective", "smooth"
    );
    private static Map<String, Integer> playerCommands = new LinkedHashMap<>();
    static void youtubeTwitchCommands(String message) {
        String msg = message.toLowerCase();
        for (String cmd: COMMANDS) {
            if (msg.contains(cmd)) {
                playerCommands.put(cmd, 100);
            }
        }
    }

    @SubscribeEvent
    static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
            Identifier.fromNamespaceAndPath(YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, "ai_timer"),
            (guiGraphics, deltaTracker) -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.gui.hud.isHidden() || mc.player == null) return;
                String label = "HELLO YOUTUBE and TWITCH Chat";
                int rightMargin = guiGraphics.guiWidth(); 
                int textWidth = mc.font.width(label);
                int xPosition = rightMargin - textWidth - 100;
                guiGraphics.text(mc.font, label, xPosition, 100, 0xFFFF2222, true);

                int xChatPos = 0;
                int yChatPos = 0;
                //List<String> chats = chatMessages.reversed();
                //§cQ§6U§eI§aC§bK§3F§dI§5X§r is the best
                for (String msg : chatMessages) {
                    guiGraphics.text(mc.font, msg, xChatPos, yChatPos, 0xFFFFFFFF, true);
                    yChatPos += 11;
                }
            }
        );
    }
}
