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
                "instructions": "What direction should the player move",
                "criteria": {
                    "forward": "Move player forward",
                    "backward": "Move player backward",
                    "left": "Move player left",
                    "right": "Move player right",
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
