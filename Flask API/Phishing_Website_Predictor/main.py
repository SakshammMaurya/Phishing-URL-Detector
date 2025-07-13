from flask import Flask, request, jsonify
import pickle
import pandas as pd
from xgboost import Booster

from feature_extractor import extract_features

app = Flask(__name__)
# model = pickle.load(open("XGBoostClassifier2.pickle.dat", "rb"))
with open("XGBoostClassifier.pkl", "rb") as file:
    model = pickle.load(file)

@app.route('/')
def home():
    return "Hello Saksham"

FEATURE_NAMES = ['Have_IP', 'Have_At', 'URL_Length', 'URL_Depth',
       'Redirection', 'https_Domain', 'TinyURL', 'Prefix_Suffix', 'DNS_Record',
       'Web_Traffic', 'Domain_Age', 'Domain_End', 'iFrame', 'Mouse_Over',
       'Right_Click', 'Web_Forwards']

@app.route('/predict', methods = ['POST'])
def predict():
    data = request.get_json()
    url = data.get("url")
    print(url) # i added
    if not url:
        return jsonify({'error':'No URL provided'}),400

    try:
        features = extract_features(url)
        print(features) # i added
        df = pd.DataFrame([features], columns=FEATURE_NAMES)
        prediction = model.predict(df)[0]
        print(prediction) # i added
        result = "Phishing" if prediction == 1 else "Legitimate"
        return jsonify({'prediction':result})
    except Exception as e:
        print("Error:",e)
        return jsonify({'error':str(e)}),500

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=True)

