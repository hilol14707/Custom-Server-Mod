package com.github.hilol14707.customservermod.commands.tpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.hilol14707.customservermod.Main;

public class TpaData {
    // requestor & targets[]
    private static ConcurrentHashMap<String, List<Request>> allRequestsDB = new ConcurrentHashMap<String, List<Request>>();

    public static void addRequest(String requestor, String target, long expireTime) {
        try {
            if (allRequestsDB.containsKey(requestor)) {
                List<Request> existingTargets = allRequestsDB.get(requestor);
                for (Request request : existingTargets) {
                    if (request.target.equals(target)) {
                        return;
                    }
                }
                existingTargets.add(new Request(target, expireTime));
                allRequestsDB.put(requestor, existingTargets);
            } else {
                List<Request> newTargets = new ArrayList<Request>();
                newTargets.add(new Request(target, expireTime));
                allRequestsDB.put(requestor, newTargets);
            }
        } catch (Exception e) {
            Main.LOGGER.warn("There was an error in TpaData.addRequest() please report this issue to the mod owner if it persists, providing the following stacktrace:");
            e.printStackTrace();
        }

    }

    public static Request getRequest(String requestor, String target) {
        try {
            if (allRequestsDB.containsKey(requestor)) {
                List<Request> existingTargets = allRequestsDB.get(requestor);
                for (Request request : existingTargets) {
                    if (request.target.equals(target)) {
                        return request;
                    }
                }
                return null;
            }
        } catch (Exception e) {
            Main.LOGGER.warn("There was an error in TpaData.getRequest() please report this issue to the mod owner if it persists, providing the following stacktrace:");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param requestor the player name requesting
     * @param target    the player name that will be accepting
     * @return true if request already exists | false if request does not exist
     */
    public static Boolean doesRequestExist(String requestor, String target) {
        return getRequest(requestor, target) == null ? false : true;
    }

    public static void removeRequest(String requestor, String target) {
        try {
            if (allRequestsDB.containsKey(requestor)) {
                List<Request> existingTargets = allRequestsDB.get(requestor);
                for (Request request : existingTargets) {
                    if (request.target.equals(target)) {
                        existingTargets.remove(request);
                        allRequestsDB.putIfAbsent(requestor, existingTargets);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Main.LOGGER.warn("There was an error in TpaData.removeRequest() please report this issue to the mod owner if it persists, providing the following stacktrace:");
            e.printStackTrace();
        }
    }

    public static class Request {
        public String target;
        public long expireTime;

        public Request(String target, long expireTime) {
            this.target = target;
            this.expireTime = expireTime;
        }
    }

    public static void main(String[] args) {
        Date d = new Date();
        long a = d.getTime();
        System.out.println(a);
    }
}