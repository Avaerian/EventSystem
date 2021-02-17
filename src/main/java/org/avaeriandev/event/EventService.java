package org.avaeriandev.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class EventService {

    // Singleton
    private static EventService instance = null;
    public static EventService getService() {
        if(instance == null) instance = new EventService();
        return instance;
    }

    private Map<Class<? extends Event>, Map<Listener, List<Method>>> eventHandlers;

    private EventService() {
        this.eventHandlers = new HashMap<>();
    }

    public void register(Listener listener) {
        Map<Class<? extends Event>, List<Method>> listenerHandlers = findHandlerMethods(listener.getClass().getDeclaredMethods());

        listenerHandlers.keySet().forEach(event -> {
            if(!eventHandlers.containsKey(event)) {
                eventHandlers.put(event, new HashMap<>());
            }

            Map<Listener, List<Method>> listenerMethods = eventHandlers.get(event);
            if(!listenerMethods.containsKey(listener)) {
                listenerMethods.put(listener, new ArrayList<>());
            }

            listenerMethods.get(listener).addAll(listenerHandlers.get(event));
        });
        
        for(Map.Entry<Class<? extends Event>, List<Method>> entry : listenerHandlers.entrySet()) {
            Class<? extends Event> event = entry.getKey();
            List<Method> methods = entry.getValue();
            
        }
    }

    public void unregister(Listener listener) {
        eventHandlers.values().forEach(listenerList -> listenerList.remove(listener));
    }

    public void execute(Event event) {
        for(Map.Entry<Listener, List<Method>> entry : eventHandlers.get(event.getClass()).entrySet()) {
            Listener listener = entry.getKey();
            List<Method> methods = entry.getValue();

            methods.forEach(method -> {
                try {
                    method.invoke(listener, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private Map<Class<? extends Event>, List<Method>> findHandlerMethods(Method[] methods) {
        Map<Class<? extends Event>, List<Method>> handlers = new HashMap<>();

        for(Method method : methods) {
            EventHandler annotation = method.getAnnotation(EventHandler.class);
            if(annotation != null) { // is present
                List<Parameter> parameters = new ArrayList<>(Arrays.asList(method.getParameters()));
                if(parameters.size() == 1 && Event.class.isAssignableFrom(parameters.get(0).getType())) {

                    // Store handler in map
                    Class<? extends Event> event = (Class<? extends Event>) parameters.get(0).getType();
                    if(!handlers.containsKey(event)) {
                        handlers.put(event, new ArrayList<>());
                    }
                    handlers.get(event).add(method);
                }
            }
        }
        return handlers;
    }

    public Set<Listener> getListeners(Class<? extends Event> event) {
        return eventHandlers.get(event).keySet();
    }

}
