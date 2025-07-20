from flask import Flask, request, jsonify
import joblib  # For loading scikit-learn models, replace with torch or tf if needed

app = Flask(__name__)

# Load your trained model (adjust path and loader depending on your framework)
MODEL_PATH = 'model21.pkl'
model = joblib.load(MODEL_PATH)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    
    # Assuming input is a list of features: {'input': [[feat1, feat2, ...]]}
    if 'input' not in data:
        return jsonify({'error': 'Missing input data'}), 400

    try:
        prediction = model.predict(data['input']).tolist()
        return jsonify({'prediction': prediction})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/', methods=['GET'])
def home():
    return "AI Model Prediction Server is Running!"

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
