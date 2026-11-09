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
import net.minecraft.world.InteractionHand;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import java.util.function.Predicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
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
    private static final int AI_HOLD_TICKS = 20; // covers the ~1s gap between AI decisions
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

    // Dig mode: hold attack long enough to finish breaking a block (~4s), ignoring new AI decisions
    private static boolean digging = false;
    private static final int DIG_TICKS = 80;

    // Per-tick stuck detection (runs every game tick, independent of the slow AI)
    private static Vec3 tickLastPos = null;
    private static Vec3 stuckOrigin = null;
    private static int stuckTicks = 0;
    private static int stuckStage = 0;
    private static final int STUCK_STEP_TICKS = 15;

    // Per-tick combat aiming
    private static LivingEntity combatTarget = null;
    private static int combatTicks = 0;

    // ---- Dev instrumentation: PROGRESS log lines, plus an opt-in test hook (env AI_TEST=1) ----
    private static final boolean AI_TEST = "1".equals(System.getenv("AI_TEST"));
    private static long devTicks = 0;
    private static Vec3 progStart = null, progLast = null;
    private static double progPath = 0;
    private static int deaths = 0;
    private static String lastMove = "-";
    private static String lastPath = "-"; // which branch of applyAIDecision ran
    private static int attacks = 0;

    static String hitDesc(Minecraft mc) {
        if (mc.hitResult instanceof BlockHitResult b)
            return BuiltInRegistries.BLOCK.getKey(mc.level.getBlockState(b.getBlockPos()).getBlock()).getPath() + "@" + b.getBlockPos().toShortString().replace(" ", "");
        return mc.hitResult == null ? "null" : mc.hitResult.getType().toString();
    }

    static void tickProgress(Minecraft mc) {
        LocalPlayer p = mc.player;
        devTicks++;
        Vec3 pos = p.position();
        if (progStart == null) { progStart = pos; progLast = pos; }
        progPath += Math.sqrt(horizSqr(pos, progLast));
        progLast = pos;

        if (devTicks == 1) YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("ENV AI_TEST={} AI_STAGE={} AI_MODE={}", System.getenv("AI_TEST"), System.getenv("AI_STAGE"), System.getenv("AI_MODE"));
        if (AI_TEST) {
            // Test worlds are copies of old chat-plays sessions: viewers' /attribute commands persist in the save
            // (tiny max health, tiny scale, ...). Reset them so every run starts from a normal player.
            String[] reset = {
                "attribute @s minecraft:max_health base set 20", "attribute @s minecraft:scale base set 1",
                "attribute @s minecraft:movement_speed base set 0.1", "attribute @s minecraft:jump_strength base set 0.42",
                "attribute @s minecraft:gravity base set 0.08", "attribute @s minecraft:step_height base set 0.6",
                "attribute @s minecraft:safe_fall_distance base set 3", "attribute @s minecraft:fall_damage_multiplier base set 1",
                "attribute @s minecraft:attack_damage base set 1", "effect clear @s",
                "effect give @s minecraft:instant_health 1 10 true"
            };
            String st0 = System.getenv("AI_STAGE");
            if (st0 != null && !st0.isEmpty()) {
                if (devTicks == 5) { stage = Integer.parseInt(st0); YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("STAGE forced to {}", stage); }
                String[] gear = { "give @s minecraft:stone_pickaxe", "give @s minecraft:stone_sword", "give @s minecraft:oak_planks 16",
                                  "give @s minecraft:oak_log 8", "give @s minecraft:cooked_beef 16", "give @s minecraft:furnace" };
                if (devTicks >= 6 && devTicks < 6 + gear.length) p.connection.sendCommand(gear[(int) (devTicks - 6)]);
            }
            if (devTicks >= 10 && devTicks < 10 + reset.length) p.connection.sendCommand(reset[(int) (devTicks - 10)]);
            if (p.isDeadOrDying() || (mc.gui.screen() != null && mc.gui.screen().getClass().getSimpleName().contains("Death"))) {
                deaths++;
                p.respawn();
                YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("DEATH #{} (hp was {})", deaths, (int) p.getHealth());
            }
            if ("wood".equals(System.getenv("AI_MODE"))) {
                // Survival stages: daytime, no gifts, no summons
                if (devTicks == 40) p.connection.sendCommand("time set day");
                if (devTicks == 60) p.connection.sendCommand("gamerule advance_time false");
                // These test worlds ship with health regeneration off; turn it on (name differs between versions)
                if (devTicks == 70) p.connection.sendCommand("gamerule natural_health_regeneration true");
            } else {
            if (devTicks == 40) p.connection.sendCommand("difficulty normal");
            if (devTicks == 60) p.connection.sendCommand("time set midnight");
            if (devTicks == 80) p.connection.sendCommand("give @s minecraft:iron_sword");
            // A zombie 6 blocks away every 30s to exercise combat
            if (devTicks > 200 && devTicks % 600 == 0) p.connection.sendCommand("summon minecraft:zombie ~6 ~ ~");
            }
        }

        if (devTicks % 100 == 0) {
            LivingEntity h = nearestHostile(mc, 16);
            YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info(
                "PROGRESS t={}s pos={},{},{} fromStart={} path={} hp={} deaths={} dig={} stage={} atk={} inv={} tgt={} hit={} scr={} move={}/{} keys={} F={} hostile={}",
                devTicks / 20, (int) pos.x, (int) pos.y, (int) pos.z,
                (int) Math.sqrt(horizSqr(pos, progStart)), (int) progPath, (int) p.getHealth(), deaths,
                digging, stuckStage, attacks, invSummary(p), gatherTarget == null ? "-" : gatherTarget.toShortString(),
                hitDesc(mc), mc.gui.screen() == null ? "-" : mc.gui.screen().getClass().getSimpleName(), lastMove, lastPath,
                (mc.options.keyUp.isDown() ? "W" : "") + (mc.options.keyLeft.isDown() ? "A" : "") + (mc.options.keyRight.isDown() ? "D" : "")
                    + (mc.options.keyDown.isDown() ? "S" : "") + (mc.options.keyJump.isDown() ? "J" : "") + (mc.options.keySprint.isDown() ? "R" : "")
                    + (mc.options.keyAttack.isDown() ? "M" : ""),
                scanDir(mc.level, p.blockPosition(), p.getDirection(), 3),
                h == null ? "none" : BuiltInRegistries.ENTITY_TYPE.getKey(h.getType()).getPath() + "@" + (int) Math.sqrt(h.distanceToSqr(p)));
        }
    }

    private static double horizSqr(Vec3 a, Vec3 b) {
        double dx = a.x - b.x, dz = a.z - b.z;
        return dx * dx + dz * dz;
    }

    /** Runs every tick: escalating wall recovery (jump -> turn right -> turn left -> face front and dig). */
    static void tickUnstuck(Minecraft mc) {
        LocalPlayer p = mc.player;
        boolean pushing = mc.options.keyUp.isDown() || mc.options.keyDown.isDown()
            || mc.options.keyLeft.isDown() || mc.options.keyRight.isDown();
        Vec3 pos = p.position();
        boolean barelyMoved = tickLastPos != null && horizSqr(pos, tickLastPos) < 0.0004; // ignore Y: jumping in place is not progress
        tickLastPos = pos;

        // Dig mode ends when the hold runs out or we got through
        if (digging && --digTicksLeft <= 0) digging = false;
        if (stuckOrigin != null && horizSqr(pos, stuckOrigin) > 1.5) { stuckOrigin = null; stuckStage = 0; digging = false; }

        if (!pushing || !barelyMoved || p.isInWater() || digging) { stuckTicks = 0; return; }
        if (++stuckTicks < STUCK_STEP_TICKS) return;
        stuckTicks = 0;
        if (stuckOrigin == null) stuckOrigin = pos;

        stuckStage++;
        if (gatherTarget != null) { // goal-driven: never turn away, jump then break whatever blocks the way
            if (stuckStage == 1) aiHold("jump", 10);
            else aiHold("mine", 40);
            if (stuckStage > 8) { gatherBlacklist.add(gatherTarget); gatherTarget = null; stuckStage = 0; }
            return;
        }
        switch (stuckStage) {
            case 1 -> aiHold("jump", 10);
            case 2 -> look(mc, 90, 0);
            case 3 -> look(mc, 180, 0);   // net 270 = 90 left of the original heading
            case 4 -> { look(mc, 90, 0); startDig(mc.player); }
            default -> stuckStage = 0;
        }
    }

    /** Runs every tick: lock on, chase, and hit the combat target directly (no crosshair/key dependence). */
    static void tickCombat(Minecraft mc) {
        if (combatTicks <= 0 || combatTarget == null) return;
        LocalPlayer p = mc.player;
        combatTicks--;
        if (!combatTarget.isAlive()) pickupTicks = 100; // walk over the drops
        if (!combatTarget.isAlive() || shouldFlee(mc, p, combatTarget) || mc.gameMode == null) {
            combatTarget = null;
            combatTicks = 0;
            return;
        }
        selectHotbar(p, st -> st.is(ItemTags.SWORDS));
        aimAt(mc, combatTarget);
        double d2 = combatTarget.distanceToSqr(p);
        if (d2 > 2.5 * 2.5) {
            aiHold("forward", 3);
            if (d2 > 5 * 5) aiHold("sprint", 3);
        }
        // Swing only when the cooldown is full so every hit does full damage
        if (d2 <= 3.2 * 3.2 && p.getAttackStrengthScale(0.5f) >= 1.0f) {
            attacks++;
            mc.gameMode.attack(p, combatTarget);
            p.swing(InteractionHand.MAIN_HAND);
        }
    }

    private static final Set<String> RANGED = Set.of("skeleton", "stray", "bogged", "pillager", "blaze", "ghast");
    private static boolean isRanged(LivingEntity m) {
        return RANGED.contains(BuiltInRegistries.ENTITY_TYPE.getKey(m.getType()).getPath());
    }
    private static int hostileCount(Minecraft mc, double radius) {
        int n = 0;
        for (Entity e : mc.level.getEntities(mc.player, mc.player.getBoundingBox().inflate(radius))) {
            if (e instanceof Enemy && e instanceof LivingEntity m && m.isAlive()) n++;
        }
        return n;
    }
    /** Run only when hurt AND swarmed by melee mobs. A lone mob dies faster to a sword than it can be outrun; ranged mobs shoot us in the back. */
    private static boolean shouldFlee(Minecraft mc, LocalPlayer p, LivingEntity t) {
        // Creepers explode in melee range: always back off instead of trading hits
        if (BuiltInRegistries.ENTITY_TYPE.getKey(t.getType()).getPath().equals("creeper") && t.distanceToSqr(p) < 7 * 7) return true;
        return p.getHealth() <= 6 && !isRanged(t) && hostileCount(mc, 8) >= 2;
    }

    // ---- Survival goals: Stage 1 = gather logs --------------------------------
    private static final int GOAL_LOGS = 8;
    private static BlockPos gatherTarget = null;
    private static final Set<BlockPos> gatherBlacklist = new java.util.HashSet<>();
    private static int gatherSearchCooldown = 0;
    private static int gatherTicks = 0;

    static int countLogs(LocalPlayer p) {
        int n = 0;
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack st = p.getInventory().getItem(i);
            if (st.is(ItemTags.LOGS)) n += st.getCount();
        }
        return n;
    }

    private static BlockPos findBlock(Minecraft mc, Predicate<BlockState> want) {
        LocalPlayer p = mc.player;
        BlockPos o = p.blockPosition();
        boolean ore = stage == 5;
        int r = ore ? 10 : 20, dyMin = ore ? -4 : -1, dyMax = ore ? 4 : 3; // surface stages never target blocks far below (= digging down)
        BlockPos best = null;
        double bestD = Double.MAX_VALUE;
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                for (int dy = dyMin; dy <= dyMax; dy++) {
                    BlockPos pos = o.offset(dx, dy, dz);
                    if (gatherBlacklist.contains(pos)) continue;
                    if (!want.test(mc.level.getBlockState(pos))) continue;
                    double d = pos.distSqr(o);
                    if (d < bestD && !(ore && lavaNear(mc, pos, 2))) { bestD = d; best = pos; }
                }
            }
        }
        return best;
    }

    /**
     * Break a block through the game mode directly. Holding the attack key only mines while the OS window has
     * the mouse grabbed, so the bot would sit idle until someone clicked in the window.
     */
    private static void mineBlock(Minecraft mc, BlockPos pos, Direction face) {
        if (mc.gameMode.continueDestroyBlock(pos, face)) mc.player.swing(InteractionHand.MAIN_HAND);
    }
    private static void mineBlock(Minecraft mc, BlockPos pos) {
        Vec3 e = mc.player.getEyePosition(), c = Vec3.atCenterOf(pos);
        mineBlock(mc, pos, Direction.getApproximateNearest(e.x - c.x, e.y - c.y, e.z - c.z));
    }

    private static void aimAtPos(LocalPlayer p, Vec3 t) {
        Vec3 eye = p.getEyePosition();
        double dx = t.x - eye.x, dy = t.y - eye.y, dz = t.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        p.setYRot((float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0));
        p.setXRot((float) Math.max(-90, Math.min(90, -Math.toDegrees(Math.atan2(dy, horiz)))));
    }

    // ---- Stage machine: 1 logs, 2 craft wooden pickaxe, 3 stone, 4 craft stone tools, 5 done (next: iron)
    private static int stage = 1;
    private static final int IRON_GOAL = 4;
    private static final int COBBLE_GOAL = 13; // stone pickaxe 3 + sword 2 + furnace 8
    private static final java.util.ArrayDeque<net.minecraft.world.item.Item> craftQueue = new java.util.ArrayDeque<>();

    private static Predicate<BlockState> gatherWanted() {
        if (stage == 1) return st -> st.is(BlockTags.LOGS);
        if (stage == 3) return st -> st.is(Blocks.STONE);
        if (stage == 5) return st -> st.is(BlockTags.IRON_ORES);
        return null;
    }

    /** Hold the right tool: best pickaxe for stone, best sword for fights. */
    private static void selectHotbar(LocalPlayer p, Predicate<ItemStack> pref) {
        for (int i = 0; i < 9; i++) {
            if (pref.test(p.getInventory().getItem(i))) { p.getInventory().setSelectedSlot(i); return; }
        }
    }

    /** Runs every tick: walk to the nearest wanted block, face it, and hold attack until it breaks. */
    static void tickGather(Minecraft mc) {
        LocalPlayer p = mc.player;
        Predicate<BlockState> want = gatherWanted();
        if (want == null || descendMode == 1 || descendMode == 2 || combatTicks > 0 || pickupTicks > 0 || digging || p.isInWater() || mc.gui.screen() != null) { gatherTarget = null; return; }
        if (stage == 1 && countLogs(p) >= GOAL_LOGS) { gatherTarget = null; return; }
        if (stage == 3 && countItems(p, st -> st.is(Items.COBBLESTONE)) >= COBBLE_GOAL) { gatherTarget = null; return; }
        if (stage == 5 && countItems(p, st -> st.is(Items.RAW_IRON)) >= IRON_GOAL) { gatherTarget = null; return; }

        if (gatherTarget != null && (!want.test(mc.level.getBlockState(gatherTarget)) || ++gatherTicks > 400)) {
            if (gatherTicks > 400) gatherBlacklist.add(gatherTarget); // could not reach it in 20s: give up on this one
            gatherTarget = null;
        }
        if (gatherTarget == null) {
            if (gatherSearchCooldown-- > 0) return;
            gatherSearchCooldown = 20;
            gatherTarget = findBlock(mc, want);
            gatherTicks = 0;
            if (gatherTarget == null) {
                // Nothing minable in sight. For stone that means it is buried: start a staircase down.
                noStoneTicks += 20;
                if ((stage == 3 || stage == 5) && noStoneTicks >= 300 && p.onGround() && descendMode == 0 && hostileCount(mc, 10) == 0) {
                    descendMode = 1;
                    descendDir = p.getDirection();
                    descendStartY = p.getY();
                    descendGoalY = p.getY() - 25; // iron: dig down to where ore is common, then tunnel
                    YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("DESCEND start y={} dir={}", (int) p.getY(), descendDir);
                }
                return;
            }
            noStoneTicks = 0;
        }

        if (stage == 3) { // stone only drops with a pickaxe in hand
            selectHotbar(p, st -> st.is(Items.STONE_PICKAXE));
            if (!p.getMainHandItem().is(Items.STONE_PICKAXE)) selectHotbar(p, st -> st.is(ItemTags.PICKAXES));
        }
        Vec3 c = Vec3.atCenterOf(gatherTarget);
        aimAtPos(p, c);
        double d = p.getEyePosition().distanceTo(c);
        double hd = Math.sqrt(horizSqr(p.position(), c));
        if (d > 3.4 && hd < 1.2) { // standing right under it but out of reach: skip this one
            gatherBlacklist.add(gatherTarget);
            gatherTarget = null;
            return;
        }
        if (d > 3.4) {
            aiHold("forward", 3);
            if (d > 8) aiHold("sprint", 3);
        } else if (mc.hitResult instanceof BlockHitResult bh && bh.getType() == HitResult.Type.BLOCK
                && p.getEyePosition().distanceTo(Vec3.atCenterOf(bh.getBlockPos())) <= 4.5) {
            // Break what we can actually see. If dirt covers the target we dig through the dirt, never "x-ray" the stone.
            mineBlock(mc, bh.getBlockPos(), bh.getDirection());
        }
    }

    // ---- Stage 2: planks -> crafting table -> sticks -> wooden pickaxe ----------
    private static BlockPos tablePos = null;
    private static int craftWait = 0;
    private static boolean pendingTake = false; // recipe was placed; next step is shift-clicking the result slot
    private static AbstractContainerMenu pendingMenu = null;
    private static int craftFail = 0;
    private static String craftNote = "-";

    static int countItems(LocalPlayer p, Predicate<ItemStack> f) {
        int n = 0;
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack st = p.getInventory().getItem(i);
            if (!st.isEmpty() && f.test(st)) n += st.getCount();
        }
        return n;
    }
    static boolean hasPickaxe(LocalPlayer p) { return countItems(p, st -> st.is(ItemTags.PICKAXES)) > 0; }

    static String invSummary(LocalPlayer p) {
        return "F" + p.getFoodData().getFoodLevel() + "/" + foodItems(p) + " st" + stage + " C" + countItems(p, st -> st.is(Items.COBBLESTONE)) + " L" + countLogs(p) + "/P" + countItems(p, st -> st.is(ItemTags.PLANKS)) + "/S" + countItems(p, st -> st.is(Items.STICK))
            + "/T" + countItems(p, st -> st.is(Items.CRAFTING_TABLE)) + "/PK" + countItems(p, st -> st.is(ItemTags.PICKAXES)) + " " + craftNote;
    }

    /** Ask the server to fill the grid with an unlocked recipe whose result matches. */
    private static boolean craftOnce(Minecraft mc, AbstractContainerMenu menu, Predicate<ItemStack> want) {
        LocalPlayer p = mc.player;
        StackedItemContents sc = new StackedItemContents();
        p.getInventory().fillStackedContents(sc);
        ContextMap ctx = SlotDisplayContext.fromLevel(mc.level);
        for (RecipeCollection col : p.getRecipeBook().getCollections()) {
            for (RecipeDisplayEntry e : col.getRecipes()) {
                List<ItemStack> res = e.resultItems(ctx);
                if (res.isEmpty() || !want.test(res.get(0)) || !e.canCraft(sc)) continue;
                mc.gameMode.handlePlaceRecipe(menu.containerId, e.id(), false);
                pendingTake = true;
                pendingMenu = menu;
                craftWait = 8;
                return true;
            }
        }
        craftFail++;
        return false;
    }

    private static boolean placeTable(Minecraft mc) {
        LocalPlayer p = mc.player;
        int slot = -1;
        for (int i = 0; i < 9; i++) if (p.getInventory().getItem(i).is(Items.CRAFTING_TABLE)) slot = i;
        if (slot < 0) { craftNote = "table-not-in-hotbar"; craftFail++; return false; }
        for (int dist = 1; dist <= 2; dist++) for (Direction d : Direction.Plane.HORIZONTAL) {
            BlockPos ground = p.blockPosition().relative(d, dist).below();
            // The target cell must not overlap the player's own hitbox, or the server refuses the placement
            if (horizSqr(p.position(), Vec3.atCenterOf(ground)) < 1.1 * 1.1) continue;
            if (isSolid(mc.level, ground, mc.level.getBlockState(ground))
                    && mc.level.getBlockState(ground.above()).isAir() && mc.level.getBlockState(ground.above(2)).isAir()) {
                p.getInventory().setSelectedSlot(slot);
                Vec3 top = Vec3.atCenterOf(ground).add(0, 0.5, 0);
                aimAtPos(p, top);
                net.minecraft.world.InteractionResult r = mc.gameMode.useItemOn(p, InteractionHand.MAIN_HAND, new BlockHitResult(top, Direction.UP, ground, false));
                p.swing(InteractionHand.MAIN_HAND);
                tablePos = ground.above();
                craftWait = 10;
                YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("TABLE place result={} at {}", r, ground);
                craftNote = "place:" + r + "@" + ground.toShortString().replace(" ", "") + "/slot" + slot + "/held=" + p.getMainHandItem().getItem();
                return true;
            }
        }
        craftNote = "no-spot-for-table";
        craftFail++;
        return false;
    }

    /** {planks, sticks} needed (besides cobble) for each craftable goal. */
    private static int[] cost(net.minecraft.world.item.Item it) {
        if (it == Items.WOODEN_PICKAXE) return new int[] {3, 2};
        if (it == Items.STONE_PICKAXE) return new int[] {0, 2};
        if (it == Items.STONE_SWORD) return new int[] {0, 1};
        return new int[] {0, 0}; // furnace: cobble only
    }

    /** Runs every tick; crafts whatever craftQueue asks for, making planks, sticks and a table as needed. */
    static void tickCraft(Minecraft mc) {
        LocalPlayer p = mc.player;
        boolean inTable = p.containerMenu instanceof CraftingMenu;
        if (mc.gui.screen() != null && !inTable) return;
        if (craftWait > 0) { craftWait--; return; }

        if (pendingTake) {
            pendingTake = false;
            mc.gameMode.handleContainerInput(pendingMenu.containerId, 0, 0, ContainerInput.QUICK_MOVE, p);
            craftWait = 6;
            return;
        }

        // Stage transitions
        if (stage == 1 && countLogs(p) >= GOAL_LOGS) { stage = 2; craftQueue.add(Items.WOODEN_PICKAXE); }
        if (stage == 3 && countItems(p, st -> st.is(Items.COBBLESTONE)) >= COBBLE_GOAL) {
            stage = 4;
            craftQueue.add(Items.STONE_PICKAXE);
            craftQueue.add(Items.STONE_SWORD);
            craftQueue.add(Items.FURNACE);
        }
        if (stage == 5 && countItems(p, st -> st.is(Items.RAW_IRON)) >= IRON_GOAL) { stage = 6; descendMode = 0; craftNote = "iron-done"; }
        if (stage != 2 && stage != 4) return;

        net.minecraft.world.item.Item head = craftQueue.peek();
        if (head != null) {
            final net.minecraft.world.item.Item h0 = head;
            if (countItems(p, st -> st.is(h0)) > 0) { craftQueue.poll(); head = craftQueue.peek(); }
        }
        if (head == null) {
            if (inTable) mc.gui.setScreen(null);
            stage++;
            craftNote = "stage" + stage;
            return;
        }
        final net.minecraft.world.item.Item goal = head;
        if (craftFail > 12) { // stuck on something location-dependent: step somewhere else and try again
            craftNote = "retry-elsewhere";
            if (inTable) mc.gui.setScreen(null);
            craftFail = 6;
            tablePos = null;
            wanderTicks = 60;
            p.setYRot(RNG.nextFloat() * 360f - 180f);
            craftWait = 80;
            return;
        }

        if (inTable) {
            craftNote = "craft-" + BuiltInRegistries.ITEM.getKey(goal).getPath();
            craftOnce(mc, p.containerMenu, st -> st.is(goal));
            return;
        }

        int needSticks = 0, needPlanks = 0;
        for (net.minecraft.world.item.Item it : craftQueue) { int[] c = cost(it); needPlanks += c[0]; needSticks += c[1]; }
        int planks = countItems(p, st -> st.is(ItemTags.PLANKS));
        int sticks = countItems(p, st -> st.is(Items.STICK));
        int tables = countItems(p, st -> st.is(Items.CRAFTING_TABLE));

        boolean tableNear = tablePos != null && mc.level.getBlockState(tablePos).is(Blocks.CRAFTING_TABLE)
            && p.position().distanceTo(Vec3.atCenterOf(tablePos)) < 8;
        if (!tableNear) {
            if (tablePos != null && !mc.level.getBlockState(tablePos).is(Blocks.CRAFTING_TABLE)) craftFail++; // placement did not take
            tablePos = null; // moved away or gone: a new one is cheaper than walking back
        }
        int stickPlanks = sticks >= needSticks ? 0 : 2 * (int) Math.ceil((needSticks - sticks) / 4.0);
        int totalPlanks = needPlanks + stickPlanks + ((tableNear || tables > 0) ? 0 : 4);

        if (planks < totalPlanks)              { craftNote = "planks"; craftOnce(mc, p.inventoryMenu, st -> st.is(ItemTags.PLANKS)); }
        else if (!tableNear && tables == 0)    { craftNote = "table";  craftOnce(mc, p.inventoryMenu, st -> st.is(Items.CRAFTING_TABLE)); }
        else if (sticks < needSticks)          { craftNote = "sticks"; craftOnce(mc, p.inventoryMenu, st -> st.is(Items.STICK)); }
        else if (!tableNear)                   { craftNote = "place";  placeTable(mc); }
        else {
            Vec3 c = Vec3.atCenterOf(tablePos);
            aimAtPos(p, c);
            if (p.getEyePosition().distanceTo(c) > 3.5) { aiHold("forward", 3); return; }
            craftNote = "open-table";
            mc.gameMode.useItemOn(p, InteractionHand.MAIN_HAND, new BlockHitResult(c, Direction.UP, tablePos, false));
            craftWait = 15;
        }
    }

    // ---- Anti-loop watchdog: no net progress for 30s -> commit to a random heading, swimming if needed ----
    private static Vec3 wdPos = null;
    private static int wdTicks = 0;
    private static int wanderTicks = 0;
    private static int wdLevel = 0;
    private static final java.util.Random RNG = new java.util.Random();

    static void tickWatchdog(Minecraft mc) {
        LocalPlayer p = mc.player;
        if (wanderTicks > 0) { wanderTicks--; wdTicks = 0; wdPos = p.position(); return; }
        boolean busy = descendMode != 0 || gatherTarget != null || stage == 2 || stage == 4 || combatTicks > 0 || mc.gui.screen() != null; // digging is NOT busy: repeated digs without progress is the loop to break
        if (busy || wdPos == null) { wdTicks = 0; wdPos = p.position(); return; }
        if (++wdTicks < 600) return;
        wdTicks = 0;
        if (horizSqr(p.position(), wdPos) >= 8 * 8) { wdLevel = 0; wdPos = p.position(); return; }
        wdPos = p.position();
        wanderTicks = 160 + 80 * Math.min(wdLevel, 4);
        wdLevel++;
        p.setYRot(RNG.nextFloat() * 360f - 180f);
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("WATCHDOG no progress: wandering {}s (level {})", wanderTicks / 20, wdLevel);
    }

    // ---- Survival: hunger. Hunt passive animals, pick up drops, eat. ---------------
    private static int pickupTicks = 0;

    static boolean isFood(ItemStack st) {
        if (st.isEmpty() || st.get(DataComponents.FOOD) == null) return false;
        return !(st.is(Items.ROTTEN_FLESH) || st.is(Items.SPIDER_EYE) || st.is(Items.POISONOUS_POTATO) || st.is(Items.PUFFERFISH));
    }
    static int foodItems(LocalPlayer p) { return countItems(p, st -> isFood(st)); }
    static boolean hungry(LocalPlayer p) {
        int f = p.getFoodData().getFoodLevel();
        return f <= 14 || (p.getHealth() < 14 && f < 20);
    }

    private static LivingEntity nearestAnimal(Minecraft mc, double radius) {
        LivingEntity best = null;
        for (Entity e : mc.level.getEntities(mc.player, mc.player.getBoundingBox().inflate(radius))) {
            if (e instanceof Animal a && !(a instanceof TamableAnimal) && a.isAlive() && !a.isBaby()) {
                if (best == null || a.distanceToSqr(mc.player) < best.distanceToSqr(mc.player)) best = a;
            }
        }
        return best;
    }

    /** True when the bot should drop what it is doing and go hunting. */
    private static boolean huntNeeded(Minecraft mc, LocalPlayer p) {
        return p.getFoodData().getFoodLevel() <= 12 && foodItems(p) == 0 && nearestAnimal(mc, 24) != null;
    }

    /** Runs every tick: eat when hungry, walk over fresh drops. */
    static void tickEat(Minecraft mc) {
        LocalPlayer p = mc.player;
        if (mc.gui.screen() != null) return;
        if (hungry(p) && p.getFoodData().getFoodLevel() < 20 && combatTicks <= 0) {
            for (int i = 0; i < 9; i++) {
                if (isFood(p.getInventory().getItem(i))) {
                    if (!p.isUsingItem()) p.getInventory().setSelectedSlot(i);
                    aiHold("use", 3); // must stay held for the whole ~1.6s or the game cancels the meal
                    return;
                }
            }
        }
        if (pickupTicks > 0 && combatTicks <= 0) {
            pickupTicks--;
            ItemEntity best = null;
            for (ItemEntity it : mc.level.getEntitiesOfClass(ItemEntity.class, p.getBoundingBox().inflate(8))) {
                if (best == null || it.distanceToSqr(p) < best.distanceToSqr(p)) best = it;
            }
            if (best != null && best.distanceToSqr(p) > 0.8) {
                aimAtPos(p, best.position());
                p.setXRot(0);
                aiHold("forward", 3);
            }
        }
    }

    /** Runs every tick, whatever the bot is doing: never walk off a 3+ block drop or into lava. */
    static void tickSafety(Minecraft mc) {
        LocalPlayer p = mc.player;
        if (!p.onGround() || p.isInWater() || !mc.options.keyUp.isDown()) return;
        String ahead = scanDir(mc.level, p.blockPosition(), p.getDirection(), 2);
        boolean deadly = ahead.startsWith("lava") || ahead.startsWith("drop3") || ahead.startsWith("drop4") || ahead.startsWith("drop5");
        if (!deadly) return;
        playerCommands.put("forward", 0);
        playerCommands.put("sprint", 0);
        mc.options.keyUp.setDown(false);
        mc.options.keySprint.setDown(false);
        if (gatherTarget != null) { gatherBlacklist.add(gatherTarget); gatherTarget = null; }
        look(mc, RNG.nextBoolean() ? 90 : -90, 0);
    }

    // ---- Staircase mining: no exposed stone nearby -> dig down in 1-block steps, then climb back out ----
    private static int descendMode = 0;            // 0 off, 1 digging down, 2 climbing out
    private static Direction descendDir = null;
    private static double descendStartY = 0, descendGoalY = 0;
    private static Vec3 tunnelOrigin = null;
    private static int noStoneTicks = 0;
    private static final int MAX_DEPTH = 26;

    private static boolean lavaNear(Minecraft mc, BlockPos c, int r) {
        for (int dx = -r; dx <= r; dx++) for (int dy = -r; dy <= r; dy++) for (int dz = -r; dz <= r; dz++)
            if (isLava(mc.level.getBlockState(c.offset(dx, dy, dz)))) return true;
        return false;
    }

    static void tickDescend(Minecraft mc) {
        LocalPlayer p = mc.player;
        if (descendMode == 0) return;
        if (combatTicks > 0 || mc.gui.screen() != null) return;
        if (p.isInWater()) { descendMode = 2; }
        if (descendMode == 1) {
            boolean done = stage == 3 && countItems(p, st -> st.is(Items.COBBLESTONE)) >= COBBLE_GOAL;
            if (stage == 5 && p.getY() <= descendGoalY) { descendMode = 3; tunnelOrigin = p.position(); return; } // deep enough: strip-mine
            if (done || p.getY() < descendStartY - MAX_DEPTH) { descendMode = 2; return; }
            selectHotbar(p, st -> st.is(Items.STONE_PICKAXE));
            if (!p.getMainHandItem().is(Items.STONE_PICKAXE)) selectHotbar(p, st -> st.is(ItemTags.PICKAXES));
            p.setYRot(descendDir.toYRot());
            p.setXRot(20);
            BlockPos ahead = p.blockPosition().relative(descendDir);
            BlockPos[] cells = { ahead.above(), ahead, ahead.below() };
            if (lavaNear(mc, ahead.below(), 2)) { descendMode = 2; return; }          // never dig toward lava
            for (BlockPos c : cells) {
                var st = mc.level.getBlockState(c);
                if (!st.getFluidState().isEmpty()) { descendMode = 2; return; }         // water/lava pocket: back off
                if (!st.isAir()) { mineBlock(mc, c); return; }                          // one block at a time, until it breaks
            }
            BlockPos floor = ahead.below(2);
            if (!isSolid(mc.level, floor, mc.level.getBlockState(floor))) { descendMode = 2; return; } // would drop into a cave
            aiHold("forward", 3);
        } else if (descendMode == 3) {
            if (stage != 5) { descendMode = 0; return; }
            if (gatherTarget != null) return; // ore in sight: tickGather walks to it and mines it
            if (horizSqr(p.position(), tunnelOrigin) > 40 * 40) { // long enough: bend the tunnel to cover new ground
                descendDir = descendDir.getClockWise();
                tunnelOrigin = p.position();
            }
            selectHotbar(p, st -> st.is(Items.STONE_PICKAXE));
            p.setYRot(descendDir.toYRot());
            p.setXRot(10);
            BlockPos ahead = p.blockPosition().relative(descendDir);
            if (lavaNear(mc, ahead, 2)) { descendDir = descendDir.getClockWise(); return; }
            for (BlockPos c : new BlockPos[] { ahead, ahead.above() }) {
                var st = mc.level.getBlockState(c);
                if (!st.getFluidState().isEmpty()) { descendDir = descendDir.getClockWise(); return; }
                if (!st.isAir() && st.getDestroySpeed(mc.level, c) >= 0) { mineBlock(mc, c); return; }
            }
            aiHold("forward", 3);
        } else {
            p.setYRot(descendDir.getOpposite().toYRot());
            p.setXRot(0);
            if (p.getY() >= descendStartY - 1) { descendMode = 0; noStoneTicks = 0; return; }
            aiHold("forward", 3);
        }
    }

    private static Direction digDir = null;
    private static int digTicksLeft = 0;

    static void startDig(LocalPlayer p) {
        digging = true;
        digDir = p.getDirection();
        digTicksLeft = 140;
    }

    /** Dig through whatever blocks the way: mine the cell ahead at feet then head height (adjacent, so always in line of sight), then step in. */
    static void tickDig(Minecraft mc) {
        if (!digging || digDir == null) return;
        LocalPlayer p = mc.player;
        if (p.isInWater() || mc.gui.screen() != null) return;
        p.setYRot(digDir.toYRot());
        p.setXRot(15);
        BlockPos ahead = p.blockPosition().relative(digDir);
        for (BlockPos c : new BlockPos[] { ahead, ahead.above() }) {
            var st = mc.level.getBlockState(c);
            if (!st.getFluidState().isEmpty()) { digging = false; return; }   // opened a water/lava pocket: stop
            if (!st.isAir() && st.getDestroySpeed(mc.level, c) >= 0) {
                boolean ok = mc.gameMode.continueDestroyBlock(c, Direction.getApproximateNearest(p.getEyePosition().subtract(Vec3.atCenterOf(c))));
                if (ok) p.swing(InteractionHand.MAIN_HAND);
                if (devTicks % 40 == 0) YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("DIGDBG cell={} block={} speed={} ok={} held={} dist={} destroying={}",
                    c.toShortString(), BuiltInRegistries.BLOCK.getKey(st.getBlock()).getPath(), st.getDestroySpeed(mc.level, c), ok,
                    p.getMainHandItem().getItem(), String.format("%.1f", p.getEyePosition().distanceTo(Vec3.atCenterOf(c))), mc.gameMode.isDestroying());
                return;
            }
        }
        if (devTicks % 40 == 0) YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("DIGDBG cells clear -> forward ahead={} above={}", mc.level.getBlockState(ahead).getBlock(), mc.level.getBlockState(ahead.above()).getBlock());
        aiHold("forward", 3);
    }

    private static boolean isFree(String scan) {
        return scan.equals("clear") || scan.startsWith("step");
    }
    private static boolean turnLeftNext = false;

    /** If a wall/drop/lava/water is within 2 blocks ahead: turn to a free side, else dig or turn around. */
    private static boolean steerAroundObstacle(Minecraft mc) { return steerAroundObstacle(mc, false); }
    private static boolean steerAroundObstacle(Minecraft mc, boolean allowWater) {
        LocalPlayer p = mc.player;
        ClientLevel lvl = mc.level;
        BlockPos o = p.blockPosition();
        Direction f = p.getDirection();
        String ahead = scanDir(lvl, o, f, 2);
        if (isFree(ahead) || (allowWater && ahead.startsWith("water"))) return false;

        String ls = scanDir(lvl, o, f.getCounterClockWise(), 3), rs = scanDir(lvl, o, f.getClockWise(), 3);
        boolean lf = isFree(ls) || (allowWater && ls.startsWith("water"));
        boolean rf = isFree(rs) || (allowWater && rs.startsWith("water"));
        String bs = scanDir(lvl, o, f.getOpposite(), 3);
        boolean behindOpen = isFree(bs) || (allowWater && bs.startsWith("water"));
        if (lf || rf) {
            boolean goLeft = lf && (!rf || turnLeftNext);
            turnLeftNext = !turnLeftNext;
            look(mc, goLeft ? -90 : 90, 0);
        } else if (ahead.startsWith("wall") && !(behindOpen && wdLevel < 2)) {
            // Boxed in with no open way back (or the watchdog already failed twice): tunnel through
            startDig(p);
        } else {
            look(mc, 180, 0); // dead end / tunnel: walk back out instead of digging deeper
        }
        aiHold("forward", AI_HOLD_TICKS);
        return true;
    }

    /** In water: face the nearest shore (solid block with open air above) and swim to it. */
    private static void escapeWater(Minecraft mc) {
        LocalPlayer p = mc.player;
        ClientLevel lvl = mc.level;
        BlockPos origin = p.blockPosition();
        Direction best = null;
        int bestDist = 9;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            for (int dist = 1; dist < bestDist; dist++) {
                BlockPos pos = origin.relative(d, dist);
                BlockPos up = pos.above();
                // Shore: solid block at feet level (or one up) with free, water-free space above it
                for (BlockPos ground : new BlockPos[] { pos, pos.below() }) {
                    BlockPos air = ground.above();
                    if (isSolid(lvl, ground, lvl.getBlockState(ground))
                            && lvl.getBlockState(air).getFluidState().isEmpty()
                            && !isSolid(lvl, air, lvl.getBlockState(air))) {
                        best = d;
                        bestDist = dist;
                    }
                }
            }
        }
        if (best != null) {
            p.setYRot(best.toYRot());
            p.setXRot(0);
            aiHold("forward", AI_HOLD_TICKS);
        }
        aiHold("jump", AI_HOLD_TICKS); // swim up
    }

    static void applyAIDecision(Minecraft mc, JsonObject json) {
        String move = aiChoice(json, "move");
        lastMove = move;
        LocalPlayer p = mc.player;
        LivingEntity target = nearestHostile(mc, 8);

        // Gathering owns the keys every tick; releasing them here would reset block-breaking progress
        if (gatherTarget != null && !(target != null && p.hasLineOfSight(target)) && !p.isInWater() && !huntNeeded(mc, p)) {
            lastPath = "gather";
            return;
        }

        // Release last round's AI keys (chat toggles stay, dig keys stay)
        for (String k : AI_KEYS) {
            if (digging && (k.equals("mine") || k.equals("forward"))) continue;
            if (k.equals("use") && p.isUsingItem()) continue;
            if (playerCommands.getOrDefault(k, 0) < CHAT_HOLD_THRESHOLD) playerCommands.put(k, 0);
        }

        // 1. Drowning comes first
        if (p.isInWater() && (p.getAirSupply() < 100 || !(digging || wanderTicks > 0))) {
            lastPath = "water";
            digging = false;
            escapeWater(mc);
            return;
        }

        // 3. Combat is rule-based, not up to the model: fight when healthy, run when hurt
        if (target != null && p.hasLineOfSight(target)) {
            double d2 = target.distanceToSqr(p);
            lastPath = "combat";
            if (digging) { digging = false; playerCommands.put("mine", 0); }
            if (shouldFlee(mc, p, target)) {
                aimAt(mc, target);
                look(mc, 180, 0);
                if (!steerAroundObstacle(mc)) aiHold("forward", AI_HOLD_TICKS);
                aiHold("sprint", AI_HOLD_TICKS);
                combatTicks = 0;
            } else {
                combatTarget = target;
                combatTicks = 80; // tickCombat chases and attacks every tick for up to 4s
                aimAt(mc, target);
            }
            return;
        }
        combatTicks = 0;

        // 3a. Starving with no food: hunt the nearest passive animal (tickCombat chases and kills it)
        if (huntNeeded(mc, p) && !digging) {
            LivingEntity prey = nearestAnimal(mc, 24);
            lastPath = "hunt";
            combatTarget = prey;
            combatTicks = 80;
            aimAt(mc, prey);
            return;
        }

        // 3b. Keep digging until the block breaks
        if (digging) {
            lastPath = "dig";
            return;
        }

        if (descendMode != 0) { lastPath = "descend"; return; }

        // Crafting: stand still near the table; tickCraft drives everything
        if ((stage == 2 || stage == 4) && wanderTicks <= 0) {
            lastPath = "craft";
            return;
        }

        if (Math.abs(p.getXRot()) > 25) p.setXRot(0); // never leave the camera staring at the sky or the ground

        if (wanderTicks > 0) {
            lastPath = "wander";
            if (!steerAroundObstacle(mc, true)) {
                aiHold("forward", AI_HOLD_TICKS);
                aiHold("sprint", AI_HOLD_TICKS);
            }
            return;
        }

        lastPath = "model";

        // 4. Otherwise follow the model, steering around walls/hazards it can't judge
        boolean forward = false;
        switch (move) {
            case "flee" -> {
                if (target != null) { aimAt(mc, target); look(mc, 180, 0); }
                forward = true;
            }
            default -> forward = true; // "forward", "stop" or anything else: never idle, keep exploring (steering avoids walls)
        }
        if (forward && !steerAroundObstacle(mc)) {
            aiHold("forward", AI_HOLD_TICKS);
            aiHold("sprint", AI_HOLD_TICKS);
        }
    }

    private static boolean aiThinking = false;
    static void askAI() {
        aiThinking = true;
        final long t0 = System.nanoTime();
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
                YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("AI_LATENCY {} ms decision: {}", (System.nanoTime() - t0) / 1_000_000, body.trim());
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

    // ---- Dev: AI_NEWWORLD=1 creates a brand-new survival world with a random seed from the title screen ----
    private static boolean newWorldRequested = false;
    private static int titleTicks = 0;

    static void tickNewWorld(Minecraft mc) {
        if (!"1".equals(System.getenv("AI_NEWWORLD")) || newWorldRequested || mc.level != null) return;
        if (!(mc.gui.screen() instanceof TitleScreen title)) return;
        if (++titleTicks < 60) return; // let resource loading settle
        newWorldRequested = true;
        String name = "ai-new-" + (System.currentTimeMillis() / 1000);
        WorldOptions opts = WorldOptions.defaultWithRandomSeed();
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("NEWWORLD {} seed={}", name, opts.seed());
        LevelSettings ls = new LevelSettings(name, GameType.SURVIVAL, LevelSettings.DifficultySettings.DEFAULT, true, WorldDataConfiguration.DEFAULT);
        mc.createWorldOpenFlows().createFreshLevel(name, ls, opts, WorldPresets::createNormalWorldDimensions, title);
    }

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc == null) return;
        tickNewWorld(mc);
        if (!aiThinking && mc.player != null) askAI();
        if (!subscribed) subscribe();

        for (Map.Entry<String, Integer> command : playerCommands.entrySet()) {
            String action = command.getKey();
            int frames = command.getValue() - 1;
            boolean direction = true;

            //YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("Command:" + action + "Frames:" + Integer.toString(frames));

            if (frames <= 0) {
                direction = false;
                command.setValue(0); // expired: otherwise it sticks at 1 and "is it still held?" checks never see 0
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

        if (mc.player != null && mc.gui.screen() == null && playerCommands.getOrDefault("mine", 0) > 0
                && !mc.mouseHandler.isMouseGrabbed()
                && mc.hitResult instanceof BlockHitResult bh && bh.getType() == HitResult.Type.BLOCK) {
            mineBlock(mc, bh.getBlockPos(), bh.getDirection()); // vanilla ignores the attack key without a grabbed mouse
        }

        if (mc.player != null && mc.level != null && combatTicks <= 0 && !mc.player.isUsingItem()
                && (playerCommands.getOrDefault("mine", 0) > 0 || digging)) {
            // Digging by hand is painfully slow and drops nothing from stone: always hold the best pickaxe while mining
            selectHotbar(mc.player, st -> st.is(Items.STONE_PICKAXE));
            if (!mc.player.getMainHandItem().is(Items.STONE_PICKAXE)) selectHotbar(mc.player, st -> st.is(ItemTags.PICKAXES));
        }

        if (mc.player != null && mc.level != null) {
            tickCombat(mc);
            tickSafety(mc);
            tickGather(mc);
            tickDescend(mc);
            tickDig(mc);
            tickEat(mc);
            tickCraft(mc);
            tickUnstuck(mc);
            tickWatchdog(mc);
            tickProgress(mc);
        }

        // Per-tick swim: in water always hold jump so the player rises and keeps their head above water
        if (mc.player != null && mc.player.isInWater()) {
            mc.options.keyJump.setDown(true);
        }

        // Per-tick auto jump: walking into a ledge on the ground -> jump (vanilla jump clears 1 block)
        LocalPlayer lp = mc.player;
        if (lp != null && mc.options.keyUp.isDown() && lp.horizontalCollision && (lp.onGround() || lp.isInWater())) {
            mc.options.keyJump.setDown(true);
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
