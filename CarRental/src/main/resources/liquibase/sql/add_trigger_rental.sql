CREATE OR REPLACE FUNCTION calculate_total_price()
RETURNS TRIGGER AS $$
DECLARE
price_per_hour DECIMAL(10, 2);
    rental_duration INTERVAL;
BEGIN
    -- Проверяем, существует ли автомобиль
    IF NOT EXISTS (SELECT 1 FROM Car WHERE id = NEW.car_id) THEN
        RAISE EXCEPTION 'CarDto with id % does not exist', NEW.car_id;
END IF;

    -- Получаем цену за час
SELECT price_per_hour INTO price_per_hour FROM Car WHERE id = NEW.car_id;

-- Вычисляем продолжительность аренды
rental_duration := NEW.end_date - NEW.start_date;

    -- Проверяем, что время окончания больше времени начала
    IF rental_duration < INTERVAL '0 seconds' THEN
        RAISE EXCEPTION 'End date must be after start date';
END IF;

    -- Устанавливаем стоимость аренды
    NEW.total_price := EXTRACT(EPOCH FROM rental_duration) / 3600 * price_per_hour;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rental_total_price_trigger
    BEFORE INSERT OR UPDATE ON Rental
                         FOR EACH ROW EXECUTE FUNCTION calculate_total_price();
