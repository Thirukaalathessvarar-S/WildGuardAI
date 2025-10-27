from flask import Flask, request, jsonify
from transformers import pipeline

# init Flask
app = Flask(__name__)

# load CLIP model once (so it doesn’t reload every request)
pipe = pipeline(
    "zero-shot-image-classification",
    model="openai/clip-vit-large-patch14"
)

@app.route("/classify", methods=["POST"])
def classify():
    data = request.get_json()
    image_url = data.get("image_url")
    labels = data.get("labels", ["animals", "humans", "landscape"])  # default labels

    result = pipe(image_url, candidate_labels=labels)
    return jsonify(result)

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=5000)
