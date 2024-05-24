from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
import secrets
import time
import os
app = Flask(__name__)

base_dir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(base_dir, 'instance', 'tokens.db')
db = SQLAlchemy(app)

class Token(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    access_token = db.Column(db.String(32), unique=True, nullable=False)
    expiration_time = db.Column(db.Integer, nullable=False)

def generate_access_token():
    token = secrets.token_hex(16)
    expiration_time = int(time.time()) + 3600
    return token, expiration_time

@app.route('/generate-token', methods=['POST'])
def generate_token():
    access_token, expiration_time = generate_access_token()
    new_token = Token(access_token=access_token, expiration_time=expiration_time)
    db.session.add(new_token)
    db.session.commit()
    return jsonify({'access_token': access_token, 'expiration_time': expiration_time})

@app.route('/delete-token/<access_token>', methods=['DELETE'])
def delete_token(access_token):
    token = Token.query.filter_by(access_token=access_token).first()
    if token:
        db.session.delete(token)
        db.session.commit()
        return jsonify({'message': 'Token deleted successfully'}), 200
    else:
        return jsonify({'message': 'Token not found'}), 404

@app.route('/update-token/<access_token>', methods=['PUT'])
def update_token(access_token):
    token = Token.query.filter_by(access_token=access_token).first()
    if token:
        token.expiration_time = int(time.time()) + 3600
        db.session.commit()
        return jsonify({'message': 'Token updated successfully'}), 200
    else:
        return jsonify({'message': 'Token not found'}), 404

@app.route('/tokens', methods=['GET'])
def get_tokens():
    tokens = Token.query.all()
    token_data = [{'access_token': token.access_token, 'expiration_time': token.expiration_time} for token in tokens]
    return jsonify(token_data)

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)
