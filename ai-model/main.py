## TODO Laya model test later
from transformers import AutoModelForMultimodalLM, AutoTokenizer
from fastapi import FastAPI, Request

repo = "stephenlb/system-one-model"
tokenizer = AutoTokenizer.from_pretrained(repo, trust_remote_code=True)
model = AutoModelForMultimodalLM.from_pretrained(
    repo, trust_remote_code=True, dtype="bfloat16", device_map="auto"
)
model = model.to("mps")

# Keep questions tiny: every question costs a model pass.
# ONE question = one model pass. Java handles sprint, jump, turning, mining, stuck recovery, aiming.
QUESTIONS = {
    "move": {
        "type": "choice",
        "instructions": "Best move? Avoid lava, water and drops.",
        "criteria": {
            "forward": "path clear",
            "backward": "back away",
            "left": "left clear",
            "right": "right clear",
            "attack": "hostile near, healthy",
            "flee": "hostile near, low hp",
            "stop": "blocked",
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
