## TODO Laya model test later
from transformers import AutoModelForMultimodalLM, AutoTokenizer
from fastapi import FastAPI, Request

repo = "stephenlb/system-one-model"
tokenizer = AutoTokenizer.from_pretrained(repo)
model = AutoModelForMultimodalLM.from_pretrained(
    repo, trust_remote_code=True, dtype="bfloat16", device_map="auto"
)
model = model.to("mps")

# Keep questions tiny: every question costs a model pass.
# Java handles sprint, jump, stuck recovery and aiming.
QUESTIONS = {
    "move": {
        "type": "choice",
        "instructions": "Best move? Avoid lava and drops.",
        "criteria": {
            "forward": "path clear",
            "backward": "back away",
            "left": "dodge or turn clear",
            "right": "dodge or turn clear",
            "flee": "hostile near, low hp",
            "approach": "hostile near, healthy",
            "stop": "blocked",
        },
    },
    "act": {
        "type": "choice",
        "instructions": "Action?",
        "criteria": {
            "attack": "hostile within 3",
            "block": "hostile near, low hp",
            "mine": "wall ahead",
            "none": "nothing",
        },
    },
    "turn": {
        "type": "choice",
        "instructions": "Turn camera?",
        "criteria": {
            "left": "left is clear",
            "right": "right is clear",
            "around": "danger ahead",
            "none": "keep",
        },
    },
}

app = FastAPI()
@app.post("/")
async def read_root(request: Request):
    body_bytes = await request.body()
    prompt = body_bytes.decode("utf-8")
    result = model.system_one(tokenizer, state=prompt, questions=QUESTIONS)
    print(result["answers"])
    return result["answers"]
