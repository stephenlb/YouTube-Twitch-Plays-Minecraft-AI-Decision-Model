## TODO Laya model test later
from transformers import AutoModelForMultimodalLM, AutoTokenizer
from fastapi import FastAPI, Request

repo = "stephenlb/system-one-model"
tokenizer = AutoTokenizer.from_pretrained(repo)
model = AutoModelForMultimodalLM.from_pretrained(
    repo, trust_remote_code=True, dtype="bfloat16", device_map="auto"
)
model = model.to("mps")

app = FastAPI()
@app.post("/")
async def read_root(request: Request):
    body_bytes = await request.body()
    prompt = body_bytes.decode("utf-8")
    result = model.system_one(
        tokenizer,
        state=prompt,
        questions={
            "movement": {
                "type": "choice",
                "instructions": (
                    "Pick the best movement for a Minecraft player. State format: "
                    "P=position, hp=health (20 max), F/L/R/B = nearest obstacle or hazard "
                    "in front/left/right/behind (clear, step@n = jumpable block, wall@n, "
                    "drop@n = fall, lava@n, water@n), M:<mob>,<dir>,<distance>,hostile = nearby mobs. "
                    "stuck:N = number of decisions the player made no progress; when stuck "
                    "pick left/right/backward toward a clear side instead of forward. "
                    "Priority: never walk toward lava or a drop; flee when hp is low "
                    "and a hostile mob is near; approach a hostile mob when hp is healthy; "
                    "otherwise explore along clear paths."
                ),
                "criteria": {
                    "forward": "Walk forward; the path ahead is clear",
                    "backward": "Walk backward, e.g. to back away while facing a mob",
                    "left": "Strafe left to dodge or circle a mob, or when left is the clear path",
                    "right": "Strafe right to dodge or circle a mob, or when right is the clear path",
                    "jump_forward": "Jump forward; a step@1 or step@2 block is ahead or a gap must be crossed",
                    "sprint_forward": "Sprint forward along a clear path with no hazards or hostile mobs close",
                    "flee": "Turn away from the nearest hostile mob and sprint; hp is low (<8) or several hostile mobs are near",
                    "approach": "Move toward the nearest hostile mob to fight it; hp is healthy (>=10)",
                    "stop": "Stand still; lava, a drop or a wall blocks every direction, or you are already in melee range",
                },
            },
            "combat": {
                "type": "choice",
                "instructions": (
                    "Should the player fight? Only attack hostile mobs. "
                    "Never attack passive mobs (cow, sheep, pig, chicken, villager)."
                ),
                "criteria": {
                    "attack": "A hostile mob is within 3 blocks; face it and strike",
                    "block": "A hostile mob is within 3 blocks and hp is low; raise the shield",
                    "none": "No hostile mob within 3 blocks",
                },
            },
            "look": {
                "type": "choice",
                "instructions": (
                    "Where should the camera turn? If stuck:N appears in the state with N>=1 "
                    "the player is pressed against an obstacle: turn toward the side that is clear."
                ),
                "criteria": {
                    "face_mob": "Aim at the nearest hostile mob",
                    "turn_around": "A hostile mob or hazard is behind the player, or every direction ahead is blocked",
                    "left": "Turn the camera slightly left",
                    "right": "Turn the camera slightly right",
                    "left_90": "Turn 90 degrees left; stuck against a wall and L is clear",
                    "right_90": "Turn 90 degrees right; stuck against a wall and R is clear",
                    "up": "Look up",
                    "down": "Look down to check the ground for a drop",
                    "none": "Keep the camera where it is",
                },
            },
            "sprint": {
                "type": "choice",
                "instructions": "Should the player sprint? Sprinting is good for fleeing and crossing clear ground, bad near drops, lava or when hp is low and food is low.",
                "criteria": {
                    "on": "Sprint; path is clear (F:clear) or fleeing a hostile mob",
                    "off": "Walk normally; a hazard, wall or drop is near, or food is below 6",
                },
            },
            "jump": {
                "type": "choice",
                "instructions": "Should the player jump? Jump to climb a one block step, hop out of water, or to hit mobs with a falling crit.",
                "criteria": {
                    "jump": "A step@1 or step@2 is ahead, the player is in water, or stuck:N with N>=1 against a one block ledge",
                    "none": "No reason to jump; do not jump toward drops or lava",
                },
            },
            "crouch": {
                "type": "choice",
                "instructions": "Should the player sneak? Sneaking stops the player falling off edges.",
                "criteria": {
                    "sneak": "A drop is next to the player and the player is not fleeing",
                    "none": "Stand and move normally",
                },
            },
            "mine": {
                "type": "choice",
                "instructions": "Should the player break the block in front? Use it to dig through walls when stuck. Never dig down or toward lava.",
                "criteria": {
                    "break_block": "wall@1 blocks the way (F:wall@1) and stuck:N with N>=2, and no lava is near",
                    "none": "Do not break blocks",
                },
            },
            #"refund": {"type": "noul", "instructions": "Does the text request a refund?"},
            #"team": {"type": "choice",
            #"instructions": "Which team should handle this?",
            #"criteria": {"billing": "Charges", "returns": "Refunds"},
        },
    )
    print(result["answers"])
    return result["answers"]
