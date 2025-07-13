# Phishing URL Detector

## Table of Content
  * [Demo](#demo)
  * [Overview](#overview)
  * [Technical Aspect](#technical-aspect)
  * [Deployement](#deployement)
  * [Directory Tree](#directory-tree)
  * [Technologies Used](#technologies-used)
  * [Team](#team)
  * [Credits](#credits)




## Demo

<p align="center">
 <a href="https://ibb.co/B5pg18QJ"><img src="https://i.ibb.co/DHJ7q3vF/Whats-App-Image-2025-07-13-at-20-25-14-03b29e65.jpg" alt="Whats-App-Image-2025-07-13-at-20-25-14-03b29e65" height = "500" widht = "50%"></a>
<a href="https://ibb.co/DxSccYH"><img src="https://i.ibb.co/rC1883R/Whats-App-Image-2025-07-13-at-20-31-09-f6e7ff5a.jpg" alt="Whats-App-Image-2025-07-13-at-20-31-09-f6e7ff5a" height = "500" widht = "50%"></a>
</p>


## Overview

This project aims to detect whether a given URL is **Phishing** or **Legitimate** using a machine learning model hosted on a Flask backend and accessed through a Jetpack Compose Android frontend.

It demonstrates an end-to-end application involving:
- Feature extraction from URLs
- Classification using a trained model
- Real-time result display in a mobile app

---

## Technical Aspect

The project is divided into three core parts:

1. **Machine Learning Model**
   - Extracts features from URLs (length, IP presence, special characters, etc.).
   - Trains an XGBoost model on a labeled dataset.
   - Saves the model using `.pkl` format for later use.

2. **Flask Backend**
   - A `/predict` route accepts a URL, extracts features, loads the model, and returns a prediction: `"Phishing"` or `"Legitimate"`.

3. **Android App (Jetpack Compose)**
   - Users can input a URL and get instant classification.
   - Retrofit is used for API communication.
   - Displays safe (✅ green shield) or unsafe (❌ red shield) icons accordingly.
   - Coil is used for image loading.

---

## Deployment

### Flask Backend

```bash
# Clone repo or go to Flask directory
cd FlaskAPI/

# Install dependencies
pip install -r requirements.txt

# Run the server
python app.py
```

## Directory Tree



```bash

Phishing URL Detector/
├── AndroidApp/
│   └── app/
│       ├── MainActivity.kt
│       └── ...
├── FlaskAPI/
│   ├── app.py
│   ├── phishing_model.pkl
│   ├── extract_features.py
│   ├── requirements.txt
│   └── ...
├── README.md
└── .gitignore

```

### Technologies Used

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kotlin/kotlin-original.svg" height="40" alt="kotlin logo"  />
  <img width="12" />
  <img src="https://cdn.simpleicons.org/android/3DDC84" height="40" alt="android logo"  />
  <img width="12" />
  <img src="https://cdn.simpleicons.org/androidstudio/3DDC84" height="40" alt="androidstudio logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/jetpackcompose/jetpackcompose-original.svg" height="40" alt="jetpackcompose logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg" height="40" alt="python logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/pandas/pandas-original.svg" height="40" alt="pandas logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/numpy/numpy-original.svg" height="40" alt="numpy logo"  />
  <img width="12" />
  <img src="https://cdn.simpleicons.org/anaconda/44A833" height="40" alt="anaconda logo"  />
  <img width="12" />
  <img src="https://skillicons.dev/icons?i=pycharm" height="40" alt="pycharm logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/flask/flask-original.svg" height="40" alt="flask logo"  />
  <img width="12" />
  <img src="https://cdn.simpleicons.org/postman/FF6C37" height="40" alt="postman logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" height="40" alt="git logo"  />
</div>

## Team
<a href="https://ibb.co/p6B1zx13"><img src="https://i.ibb.co/6c7yPmy4/Whats-App-Image-2025-07-01-at-13-26-16-37a821cc.jpg" alt="my image" border="0" height ="150" /></a> |
-|
Saksham Maurya


## Credits
This project was inspired and supported by the following resources:

### Datasets
- [https://www.phishtank.com/developer_info.php](https://www.phishtank.com/developer_info.php)
- [https://www.unb.ca/cic/datasets/url-2016.html](https://www.unb.ca/cic/datasets/url-2016.html)

