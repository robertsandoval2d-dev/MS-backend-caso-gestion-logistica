# Iniciar componentes base
kubectl apply -f 00-namespace.yaml
kubectl apply -f 01-configmap.yaml
kubectl apply -f 02-databases.yaml

Write-Host "`n[1/3] Levantando bases de datos y namespace..." -ForegroundColor Cyan
Start-Sleep -Seconds 5

# Levantar infraestructura central
kubectl apply -f 03-infraestructura.yaml
Write-Host "`n[2/3] Esperando que el Config Server y Eureka esten listos (esto toma unos 30-45s)..." -ForegroundColor Yellow

# Espera inteligente hasta que los pods centrales estén en estado Ready
kubectl wait --for=condition=ready pod -l app=config-server -n ferreteria-psa --timeout=60s
kubectl wait --for=condition=ready pod -l app=eureka-server -n ferreteria-psa --timeout=60s

# Levantar microservicios de negocio y gateway
Write-Host "`n[3/3] Listo. Desplegando microservicios y API Gateway..." -ForegroundColor Green
kubectl apply -f 04-microservicios.yaml
kubectl apply -f 05-gateway-ingress.yaml

Write-Host "`n¡Todo el sistema ha sido enviado a producción local!" -ForegroundColor Magenta
Write-Host "Ejecuta 'kubectl get pods -n ferreteria-psa -w' para ver el estado." -ForegroundColor DarkGray

