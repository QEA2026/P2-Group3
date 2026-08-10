FROM python:3.11-slim

WORKDIR /employee_app

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 7070

CMD ["python", "app.py"]