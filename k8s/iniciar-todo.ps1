# levantar.ps1 — Ferretería PSA en Kubernetes
# Ejecutar desde la carpeta k8s/

Write-Host "Desplegando en Kubernetes" -ForegroundColor Cyan

Write-Host "`n1 Namespace, ConfigMap y Secrets" -ForegroundColor Yellow
kubectl apply -f 00-namespace.yaml

Write-Host "`n2 Bases de datos (SQL Server, MySQL, PostgreSQL)" -ForegroundColor Yellow
kubectl apply -f 01-databases.yaml
Write-Host "  Esperando SQL Server"
kubectl wait --for=condition=ready pod -l app=sqlserver-seguridad -n ferreteria-psa --timeout=180s
Write-Host "  Esperando MySQL"
kubectl wait --for=condition=ready pod -l app=mysql-glogistica -n ferreteria-psa --timeout=180s
Write-Host "  Esperando PostgreSQL"
kubectl wait --for=condition=ready pod -l app=postgres-gcomercial -n ferreteria-psa --timeout=180s
Write-Host "  Esperando inicializacion de base Seguridad"
kubectl wait --for=condition=complete job/init-seguridad-db -n ferreteria-psa --timeout=180s

Write-Host "`n3 Config Server y Eureka" -ForegroundColor Yellow
kubectl apply -f 02-infraestructura.yaml
Write-Host "  Esperando Config Server"
kubectl wait --for=condition=ready pod -l app=config-server -n ferreteria-psa --timeout=120s
Write-Host "  Esperando Eureka"
kubectl wait --for=condition=ready pod -l app=eureka-server -n ferreteria-psa --timeout=120s

Write-Host "`n Microservicios y Gateway" -ForegroundColor Yellow
kubectl apply -f 03-microservicios.yaml
kubectl apply -f 04-gateway-ingress.yaml

Write-Host "`n Estado del cluster:" -ForegroundColor Cyan
kubectl get pods -n ferreteria-psa
