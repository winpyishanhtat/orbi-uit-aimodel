from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
import pickle
import traceback
import xgboost as xgb

app = Flask(__name__)
CORS(app)

# === LOAD EVERYTHING ONCE AT STARTUP ===
try:
    # This is the full classifier object, needed for .predict_proba()
    model = xgb.XGBClassifier()
    model.load_model('model_corrected.json')

    # Load the LabelEncoder
    with open('label_encoder_corrected.pkl', 'rb') as f:
        label_encoder = pickle.load(f)

    # Load the StandardScaler
    with open('scaler_corrected.pkl', 'rb') as f:
        scaler = pickle.load(f)
    
    print("✅ Model, encoder, and scaler loaded successfully!")

except FileNotFoundError as e:
    print(f"❌ ERROR: Could not load model files. Make sure they are in the correct directory. Details: {e}")
    model, label_encoder, scaler = None, None, None


INPUT_KEYS = [
    "programming", "math", "ai_ml", "cloud", "business", "networking", "security", "hardware",
    "interest_ai", "interest_cloud", "interest_business", "interest_security", "interest_low_level",
    "career_research", "career_enterprise", "career_startup",
    "gpa", "creativity", "time_management"
]

@app.route('/')
def home():
    return "Server is running. Use POST /predict with JSON data."

@app.route('/predict', methods=['POST'])
def predict():
    try:
        if not all([model, label_encoder, scaler]):
            return jsonify({"error": "Model, encoder, or scaler not loaded properly on startup"}), 500

        data = request.get_json()
        if not data:
            return jsonify({"error": "No JSON received"}), 400

        # --- The prediction logic is now much cleaner and faster ---

        # 1. Extract and scale features
        feature_list = [float(data.get(key, 0)) for key in INPUT_KEYS]
        scaled_features = scaler.transform([feature_list])
        
        # 2. Predict probabilities using the globally loaded model
        prediction_probs = model.predict_proba(scaled_features)[0]

        # 3. Get top 3 recommendations
        top_n = 3
        top_indices = np.argsort(prediction_probs)[-top_n:][::-1]

        recommendations = []
        for i in top_indices:
            major_label = label_encoder.inverse_transform([i])[0]
            probability = round(float(prediction_probs[i]) * 100, 2)
            recommendations.append({"major": major_label, "probability": probability})

        return jsonify({
            "recommendations": recommendations,
            "status": "success"
        })

    except Exception as e:
        traceback.print_exc()
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)