package com.youtubetwitchplaysminecraftwithaidecisionmodel.plays;

// Decision Models 
// Decision Models 
// Decision Models 
// https://docs.liquid.ai/lfm/models/d1-2b
// https://huggingface.co/LiquidAI/d1-2B
// Decision Models 
// Decision Models 

/*
✅ keyUp / backKey / keyLeft / keyRight — Movement controls (W, S, A, D)
✅ keyJump — Jump
✅ keyShift — Sneak (Crouch)
💜
TODO Hotbar 
keyAttack — Attack / Break block (Left-click)
keySprint — Sprint
keyInventory — Open Inventory
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
// TODO  HOLD SHIELD
// TODO DRAW OVERLAY
// TODO
// TODO CLick COunter
// TODO
// TODO
// TODO  LOOK DOWN
// TODO Block Count - every   block broken increments counter
// TODO
// TODO STOP command
// TODO TURN ARUND command
// TODO /gift bread 3
// TODO when death block randomizes
// TODO Add Death Counter
// TODO Change Loacation command x y coords GOTO TP Telepoprt



// TODO
// TODO Spaw mobs
// TODO
// TODO
// TODO lookup lookright lookleft lookdown
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

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import java.net.http.HttpRequest.BodyPublishers;
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

    private static List<String> ACTION_COMMANDS = List.of(
        "swap", "drop", "use", "attack", "pick",
        "inventory", "chat", "escape",
        "look up", "look down", "look left", "look right",
        "turn around", "slot", "next item", "prev item"
    );
    static void look(Minecraft mc, float yaw, float pitch) {
        mc.player.setYRot(mc.player.getYRot() + yaw);
        mc.player.setXRot(Math.max(-90f, Math.min(90f, mc.player.getXRot() + pitch)));
    }
    static void actionCommadns(Minecraft mc, String message) {
        String msg = message.toLowerCase();

        // Action commands
        for (String cmd: ACTION_COMMANDS) {
            if (!msg.contains(cmd)) continue;

            switch (cmd) {
                case "swap" -> KeyMapping.click(mc.options.keySwapOffhand.getKey());
                case "drop" -> KeyMapping.click(mc.options.keyDrop.getKey());
                case "use"  -> KeyMapping.click(mc.options.keyUse.getKey());
                case "attack" -> KeyMapping.click(mc.options.keyAttack.getKey());
                case "pick" -> KeyMapping.click(mc.options.keyPickItem.getKey());
                case "inventory" -> KeyMapping.click(mc.options.keyInventory.getKey());
                case "chat"      -> mc.gui.setScreen(new net.minecraft.client.gui.screens.ChatScreen("", false));
                case "escape"    -> { if (mc.gui.screen() != null) mc.gui.screen().onClose(); }
                case "look up"    -> look(mc, 0, -30);
                case "look down"  -> look(mc, 0, 30);
                case "look left"  -> look(mc, -30, 0);
                case "look right" -> look(mc, 30, 0);
                case "turn around" -> look(mc, 180, 0);
                case "slot" -> {
                    Matcher m = Pattern.compile("slot ?([1-9])").matcher(msg);
                    if (m.find()) {
                        mc.player.getInventory().setSelectedSlot(Integer.parseInt(m.group(1)) - 1);
                    }
                }
                case "next item" -> mc.player.getInventory().setSelectedSlot((mc.player.getInventory().getSelectedSlot() + 1) % 9);
                case "prev item" -> mc.player.getInventory().setSelectedSlot((mc.player.getInventory().getSelectedSlot() + 8) % 9);
            }
        }
    }

    // ---- AI decision handling -------------------------------------------------
    private static final int AI_HOLD_TICKS = 10;
    private static final int CHAT_HOLD_THRESHOLD = 1_000_000; // chat toggles hold ~10M ticks
    private static final List<String> AI_KEYS = List.of(
        "forward", "backward", "left", "right", "sprint", "jump", "crouch", "mine", "use"
    );

    private static String aiChoice(JsonObject json, String question) {
        if (!json.has(question) || !json.get(question).isJsonObject()) return "none";
        JsonObject o = json.getAsJsonObject(question);
        return o.has("choice") && !o.get("choice").isJsonNull() ? o.get("choice").getAsString() : "none";
    }

    /** Hold a key for a few ticks unless chat is already holding it on. */
    private static void aiHold(String key, int ticks) {
        if (playerCommands.getOrDefault(key, 0) >= CHAT_HOLD_THRESHOLD) return;
        playerCommands.put(key, ticks);
    }

    private static LivingEntity nearestHostile(Minecraft mc, double radius) {
        LivingEntity best = null;
        for (Entity e : mc.level.getEntities(mc.player, mc.player.getBoundingBox().inflate(radius))) {
            if (e instanceof Enemy && e instanceof LivingEntity m && m.isAlive()) {
                if (best == null || m.distanceToSqr(mc.player) < best.distanceToSqr(mc.player)) best = m;
            }
        }
        return best;
    }

    /** Snap the camera onto the target's body. */
    private static void aimAt(Minecraft mc, LivingEntity target) {
        LocalPlayer p = mc.player;
        Vec3 eye = p.getEyePosition();
        Vec3 t = target.position().add(0, target.getBbHeight() * 0.5, 0);
        double dx = t.x - eye.x, dy = t.y - eye.y, dz = t.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        p.setYRot((float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0));
        p.setXRot((float) Math.max(-90, Math.min(90, -Math.toDegrees(Math.atan2(dy, horiz)))));
    }

    // Stuck detection: consecutive decisions where the player tried to move but barely did
    private static int stuckCount = 0;
    private static Vec3 lastDecisionPos = null;
    private static boolean lastWantedMove = false;

    static void applyAIDecision(Minecraft mc, JsonObject json) {
        String move = aiChoice(json, "move");
        String act = aiChoice(json, "act");
        String turn = aiChoice(json, "turn");
        LocalPlayer p = mc.player;
        LivingEntity target = nearestHostile(mc, 8);
        boolean hostileClose = target != null && target.distanceToSqr(p) < 25;

        // Stuck detection (AI or chat wanted movement, but we barely moved)
        Vec3 pos = p.position();
        boolean chatMoving = false;
        for (String k : List.of("forward", "backward", "left", "right")) {
            if (playerCommands.getOrDefault(k, 0) > 0) chatMoving = true;
        }
        boolean wanted = lastWantedMove || chatMoving;
        stuckCount = (lastDecisionPos != null && wanted && pos.distanceToSqr(lastDecisionPos) < 0.25) ? stuckCount + 1 : 0;
        lastDecisionPos = pos;
        lastWantedMove = !move.equals("stop") && !move.equals("none");

        // Release last round's AI keys; when stuck also release chat-held ones
        boolean stuck = stuckCount >= 2;
        for (String k : AI_KEYS) {
            if (stuck || playerCommands.getOrDefault(k, 0) < CHAT_HOLD_THRESHOLD) playerCommands.put(k, 0);
        }

        // Camera
        switch (turn) {
            case "left"   -> look(mc, -45, 0);
            case "right"  -> look(mc, 45, 0);
            case "around" -> look(mc, 180, 0);
            default -> {}
        }

        // Movement
        boolean forward = false;
        switch (move) {
            case "forward" -> forward = true;
            case "backward", "left", "right" -> aiHold(move, AI_HOLD_TICKS);
            case "approach" -> { if (target != null) { aimAt(mc, target); forward = true; } }
            case "flee" -> {
                if (target != null) { aimAt(mc, target); look(mc, 180, 0); forward = true; aiHold("sprint", AI_HOLD_TICKS); }
            }
            default -> {} // stop
        }
        if (forward) {
            aiHold("forward", AI_HOLD_TICKS);
            if (!hostileClose && move.equals("forward")) aiHold("sprint", AI_HOLD_TICKS);
        }

        // Auto jump: blocked by a ledge, or swimming
        if (p.horizontalCollision || p.isInWater()) aiHold("jump", 4);

        // Stuck recovery, escalating: jump -> turn 90 -> dig
        if (stuck) {
            aiHold("forward", AI_HOLD_TICKS);
            aiHold("jump", 4);
            if (stuckCount >= 3) look(mc, stuckCount % 2 == 0 ? 90 : -90, 0);
            if (stuckCount >= 5) aiHold("mine", AI_HOLD_TICKS);
        }

        // Action
        switch (act) {
            case "attack" -> {
                if (target != null) {
                    aimAt(mc, target);
                    if (target.distanceToSqr(p) <= 3.5 * 3.5) aiHold("mine", AI_HOLD_TICKS);
                }
            }
            case "block" -> aiHold("use", AI_HOLD_TICKS);
            case "mine"  -> aiHold("mine", AI_HOLD_TICKS);
            default -> {}
        }
    }

    private static boolean aiThinking = false;
    static void askAI() {
        aiThinking = true;
        // Shell out to curl: macOS Local Network privacy blocks the JVM but not curl
        CompletableFuture.supplyAsync(() -> {
                try {
                    String question = getClientWorldStateString(5, 5);
                    //String question = "new world";
                    Process p = new ProcessBuilder("curl", "-s", "-m", "10",
                        "http://192.168.50.50:8000", "--data", question).start();
                    String out = new String(p.getInputStream().readAllBytes());
                    if (p.waitFor() != 0) throw new RuntimeException("curl failed: " + p.exitValue());
                    return out;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .thenAccept(body -> {
                JsonObject json = new Gson().fromJson(body, JsonObject.class);
                YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("AI decision: {}", body);
                Minecraft mc = Minecraft.getInstance();
                mc.execute(() -> {
                    if (mc.player == null) return;
                    applyAIDecision(mc, json);
                });
            })
            .whenComplete((ok, ex) -> {
                aiThinking = false;
                if (ex != null) YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("AI request FAILED", ex);
            });
    }

    static void subscribe() {
        subscribed = true;
        String endpoint = "https://ps.pndsn.com";
        String channel = System.getenv("YOUTUBE_TWITCH_CHANNEL");
        String url = endpoint + "/subscribe/demo/" + channel + "/0/" + timetoken;

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

                          // Run Slash Commands
                          slashCommands(mc, message);

                          // Action Commands
                          actionCommadns(mc, message);

                          // Colorize YouTube and Twitch chat
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

    private static final Set<String> SLASH_COMMANDS = Set.of(
        //"/execute",
        "/tp", "/weather", "/give",
        "/summon", "/add", "/gamemode",
        "/time", "/effect", 
        "/attribute", "/kill",
        "/seed", "/gamerule", //, "/particle",
        "/playsound"//, "/worldborder",
        // , "/title", "/data"
        //"/fill", "/setblock"
        // "/setblock",
        // "/exectue"
    );
    static void slashCommands(Minecraft mc, String message) {
        String command = message.trim().split(" ")[0].trim();
        if (!SLASH_COMMANDS.contains(command)) return;
        // Show message on screen is recoginzed by our filter
        mc.player.connection.sendChat(message.trim());
        // Send without slash command
        mc.player.connection.sendCommand(message.trim().substring(1));
    }

    /*
    @SubscribeEvent
    static void onServerTick(ClientTickEvent.Post event) {
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("SERVER TICK EVENT");
    }
    */

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc == null) return;
        if (!aiThinking && mc.player != null) askAI();
        if (!subscribed) subscribe();

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
            KeyMapping key = switch (action) {
                case "perspective" -> mc.options.keyTogglePerspective;
                case "smooth"      -> mc.options.keySmoothCamera;
                case "inventory"   -> mc.options.keyInventory;
                case "mine"        -> mc.options.keyAttack;
                case "use"         -> mc.options.keyUse;
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
            }
        }
    }

    private static List<String> COMMANDS = List.of(
        "sprint", "jump", "crouch", "right", "left",
        "forward", "backward", "mine", "stop"
    );
    static List parseArgs(String command, String message) {
        String regex = command + " +([a-z0-9]+),? +([a-z0-9]+),? +([a-z0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        List<String> args = new ArrayList<String>();
        while (matcher.find()) {
            args.add(matcher.group(1));
            args.add(matcher.group(2));
            args.add(matcher.group(3));
        }
        return args;
    }
    static void teleport(String command, String message) {
        Integer x = 100;
        Integer y = 100;
        Integer z = 100;
        List args = parseArgs(command, message);
        Minecraft mc = Minecraft.getInstance();

        if (mc != null) {
            mc.player.setPos(x, y, z);
        }
    }
    private static Map<String, Integer> playerCommands = new LinkedHashMap<>();
    static void youtubeTwitchCommands(String message) {
        String msg = message.toLowerCase();

        // "look left" / "turn around" are camera commands, not movement
        if (msg.contains("look ") || msg.contains("turn ")) return;

        for (String cmd: COMMANDS) {
            if (msg.contains(cmd)) {
                // Allow for change of direction
                switch (cmd) {
                    case "forward"  -> playerCommands.put("backward", 0);
                    case "backward" -> playerCommands.put("forward", 0);
                    case "left"     -> playerCommands.put("right", 0);
                    case "right"    -> playerCommands.put("left", 0);
                }

                // Stop all movement actions
                if (cmd == "stop") {
                    for (String cmd2: COMMANDS) {
                        playerCommands.put(cmd2, 0);
                    }
                }

                Integer val = playerCommands.getOrDefault(cmd, 0);
                if (val > 0) {
                    playerCommands.put(cmd, 0);
                }
                else {
                    playerCommands.put(cmd, 10000000);
                }
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

                // Movement instructions with active indicators (value > 0 means active)
                int yCmdPos = 115;
                String header = "Type a command in chat to toggle:";
                guiGraphics.text(mc.font, header, rightMargin - mc.font.width(header) - 10, yCmdPos, 0xFFFFFF55, true);
                yCmdPos += 11;
                for (String cmd : COMMANDS) {
                    boolean isStop = cmd.equals("stop");
                    boolean active = playerCommands.getOrDefault(cmd, 0) > 0;
                    String line = isStop
                        ? "stop - release all"
                        : (active ? "[ON]  " : "[off] ") + cmd;
                    int color = isStop ? 0xFFFF5555 : (active ? 0xFF55FF55 : 0xFFAAAAAA);
                    guiGraphics.text(mc.font, line, rightMargin - mc.font.width(line) - 10, yCmdPos, color, true);
                    yCmdPos += 11;
                }

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
    /**
     * Compact world state for the LLM (~100-200 chars instead of thousands of blocks).
     * Example:
     *   P:120,64,-30 facing:north hp:20 food:18 ground:1
     *   F:step@2 L:clear R:wall@1 B:drop3@1
     *   M:zombie,F,3 M:cow,L,5
     * bRad = how many blocks ahead to scan in each direction, mRad = mob radius.
     */
    public static String getClientWorldStateString(int bRad, int mRad) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel lvl = mc.level;
        LocalPlayer p = mc.player;
        if (lvl == null || p == null) return "No world state available";

        Direction facing = p.getDirection();
        BlockPos pPos = p.blockPosition();

        StringBuilder sb = new StringBuilder();
        sb.append("P:").append(pPos.getX()).append(",").append(pPos.getY()).append(",").append(pPos.getZ())
          .append(" facing:").append(facing.getName())
          .append(" hp:").append((int) p.getHealth())
          .append(" food:").append(p.getFoodData().getFoodLevel())
          .append(" ground:").append(p.onGround() ? 1 : 0)
          .append(p.isInWater() ? " in:water" : "")
          .append(p.isInLava() ? " in:lava" : "")
          .append(p.isOnFire() ? " fire:1" : "")
          .append("\n");

        // Terrain: first obstacle/hazard in each relative direction
        sb.append("F:").append(scanDir(lvl, pPos, facing, bRad))
          .append(" L:").append(scanDir(lvl, pPos, facing.getCounterClockWise(), bRad))
          .append(" R:").append(scanDir(lvl, pPos, facing.getClockWise(), bRad))
          .append(" B:").append(scanDir(lvl, pPos, facing.getOpposite(), bRad))
          .append("\n");

        // Mobs: nearest 3 only, with relative direction (F/B/L/R) and distance
        List<LivingEntity> mobs = new ArrayList<>();
        for (Entity e : lvl.getEntities(p, p.getBoundingBox().inflate(mRad))) {
            if (e instanceof LivingEntity m) mobs.add(m);
        }
        // Hostile mobs first, then by distance
        mobs.sort((a, b) -> {
            int h = Boolean.compare(b instanceof Enemy, a instanceof Enemy);
            return h != 0 ? h : Double.compare(a.distanceToSqr(p), b.distanceToSqr(p));
        });
        for (int i = 0; i < Math.min(3, mobs.size()); i++) {
            LivingEntity m = mobs.get(i);
            String id = BuiltInRegistries.ENTITY_TYPE.getKey(m.getType()).getPath();
            double dx = m.getX() - p.getX(), dz = m.getZ() - p.getZ();
            double fwd = dx * facing.getStepX() + dz * facing.getStepZ();
            Direction right = facing.getClockWise();
            double rgt = dx * right.getStepX() + dz * right.getStepZ();
            String rel = Math.abs(fwd) >= Math.abs(rgt) ? (fwd >= 0 ? "F" : "B") : (rgt >= 0 ? "R" : "L");
            sb.append("M:").append(id).append(",").append(rel).append(",").append((int) Math.sqrt(m.distanceToSqr(p)))
              .append(m instanceof Enemy ? ",hostile" : "").append(" ");
        }
        return sb.toString().trim();
    }

    /** Describes the first thing that matters walking up to `max` blocks in direction d. */
    private static String scanDir(ClientLevel lvl, BlockPos origin, Direction d, int max) {
        for (int dist = 1; dist <= max; dist++) {
            BlockPos feet = origin.relative(d, dist);
            BlockState f0 = lvl.getBlockState(feet);
            BlockState f1 = lvl.getBlockState(feet.above());
            BlockState f2 = lvl.getBlockState(feet.above(2));

            if (isLava(f0) || isLava(f1)) return "lava@" + dist;
            if (isSolid(lvl, feet, f0)) {
                // Blocked at feet level: 1-high step is jumpable, otherwise a wall
                boolean headroom = !isSolid(lvl, feet.above(), f1) && !isSolid(lvl, feet.above(2), f2);
                return (headroom ? "step@" : "wall@") + dist;
            }
            if (f0.getFluidState().isEmpty() == false) return "water@" + dist;

            // Open at feet: look for ground below
            int drop = 0;
            BlockPos below = feet.below();
            while (drop < 5 && !isSolid(lvl, below, lvl.getBlockState(below))) {
                if (isLava(lvl.getBlockState(below))) return "lava@" + dist;
                drop++;
                below = below.below();
            }
            if (drop >= 2) return "drop" + (drop >= 5 ? "5+" : drop) + "@" + dist;
        }
        return "clear";
    }

    private static boolean isSolid(ClientLevel lvl, BlockPos pos, BlockState s) {
        return !s.getCollisionShape(lvl, pos).isEmpty();
    }

    private static boolean isLava(BlockState s) {
        return BuiltInRegistries.BLOCK.getKey(s.getBlock()).getPath().equals("lava");
    }
}
