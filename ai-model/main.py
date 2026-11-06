## TODO Laya model test later
from transformers import AutoModelForMultimodalLM, AutoTokenizer
from fastapi import FastAPI

repo = "stephenlb/system-one-model"
tokenizer = AutoTokenizer.from_pretrained(repo)
model = AutoModelForMultimodalLM.from_pretrained(
    repo, trust_remote_code=True, dtype="bfloat16", device_map="auto"
)

app = FastAPI()
@app.get("/")
def read_root():
    result = model.system_one(
        tokenizer,
        state="I was charged twice for order A-104.",
        questions={
            "refund": {"type": "noul", "instructions": "Does the text request a refund?"},
            "team": {
            "type": "choice",
            "instructions": "Which team should handle this?",
            "criteria": {"billing": "Charges", "returns": "Refunds"},
            },
        },
    )
    print(result["answers"])
    return result["answers"]
